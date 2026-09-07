package com.ftn.service;

import com.ftn.dto.CepMeasurementRequest;
import com.ftn.model.AirPollutionEvent;
import com.ftn.model.User;
import com.ftn.model.messages.Warning;
import com.ftn.repository.UserRepository;
import com.ftn.repository.WarningRepository;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CepStreamService {

    private final KieContainer kieContainer;
    private final WarningRepository warningRepository;
    private final UserRepository userRepository;

    private KieSession kieSession;

    public CepStreamService(
            KieContainer kieContainer,
            WarningRepository warningRepository,
            UserRepository userRepository
    ) {
        this.kieContainer = kieContainer;
        this.warningRepository = warningRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        this.kieSession = kieContainer.newKieSession("k-session-cep");
        System.out.println(">> CEP streaming KieSession initialized - persistent for the app lifetime.");
    }

    @PreDestroy
    public void destroy() {
        if (this.kieSession != null) {
            this.kieSession.dispose();
        }
    }

    /**
     * Prima JEDNO merenje po pozivu (svakih ~10s od simulatora) i insertuje ga
     * u TRAJNU sesiju. Window:length(3)/window:length(2) u DRL-u zbog toga
     * ispravno prati "poslednja N merenja ikad", umesto da se resetuje na
     * svaki poziv kao što bi se desilo sa batch-om i kratkotrajnom sesijom.
     */
    public synchronized List<Warning> streamMeasurement(CepMeasurementRequest req) {

        long timestampMillis = System.currentTimeMillis();

        AirPollutionEvent event = new AirPollutionEvent(
                req.getPm25(),
                req.getPm10(),
                req.getNo2(),
                req.getO3(),
                req.getCo2(),
                req.getWindSpeed(),
                req.isPrecipitation(),
                req.getTemperature(),
                req.getHumidity(),
                req.getPressure(),
                timestampMillis,
                req.getEmail()
        );

        System.out.println("CEP Event [t=" + timestampMillis + "]: PM2.5=" + req.getPm25()
                + ", Wind=" + req.getWindSpeed());

        // 1. Insert jednog merenja u trajnu sesiju
        this.kieSession.insert(event);

        // 2. Pokreni CEP pravila - window:length(3)/(2) automatski gledaju
        // poslednja N event-a ikad insertovanih (rolling window)
        int fired = this.kieSession.fireAllRules();
        System.out.println(">> Broj okinutih CEP pravila: " + fired);

        // 3. Pokupi sve nove Warning fakte generisane u OVOM ciklusu
        Collection<FactHandle> warningHandles =
                kieSession.getFactHandles(new ClassObjectFilter(Warning.class));

        if (warningHandles.isEmpty()) {
            return Collections.emptyList();
        }

        User user = userRepository.findByEmail(req.getEmail()).orElse(null);
        List<Warning> result = new ArrayList<>();

        for (FactHandle fh : warningHandles) {
            Warning generated = (Warning) kieSession.getObject(fh);

            Warning toSave = new Warning();
            toSave.setType(generated.getType());
            toSave.setContent(generated.getContent());
            toSave.setTimestamp(generated.getTimestamp());
            toSave.setUser(user);

            if (user != null) {
                warningRepository.save(toSave);
            }

            result.add(toSave);

            // 4. Retract Warning fakat odmah - sprečava da se isto
            // upozorenje ponovo vrati u sledećem pozivu. AlertState fakti
            // OSTAJU u sesiji namerno - oni prate kontinuitet alarma.
            kieSession.delete(fh);
        }

        return result;
    }
}