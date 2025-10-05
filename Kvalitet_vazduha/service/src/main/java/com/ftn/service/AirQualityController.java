package com.ftn.service;

import com.ftn.model.AirQualityStatus;
import com.ftn.model.AirQualityCategory;
import com.ftn.model.AirPollutionEvent;
import com.ftn.model.messages.Warning;
import com.ftn.model.*;
import com.ftn.dto.PollutantHistoryDTO;
import com.ftn.service.AirQualityService;
import com.ftn.util.KnowledgeSessionHelper;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.ftn.service.WarningRepository;
import com.ftn.service.UserRepository;

@RestController
@RequestMapping("/api/air")
@CrossOrigin(origins = "http://localhost:4200")
public class AirQualityController {

    private final KieContainer kieContainer;
    private KieSession kSession;

    private Warning warning;

    private final WarningRepository warningRepository;
    private final UserRepository userRepository;

    @Autowired
    private AirQualityService airQualityService;

    public AirQualityController(KieContainer kieContainer, WarningRepository warningRepository, UserRepository userRepository) {
        this.kieContainer = kieContainer;
        this.warningRepository = warningRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/pollutants/last24h")
    public ResponseEntity<List<PollutantHistoryDTO>> getPollutantHistoryLast24h() {
        return ResponseEntity.ok(airQualityService.getPollutantHistoryLast24h());
    }

    @PostConstruct
    public void init() {
        // koristi istu sesiju (npr. "k-session-cep") kroz ceo život aplikacije
        this.kSession = kieContainer.newKieSession("k-session-cep");

        // ako koristiš status kao fact:
        this.warning = new Warning();
        this.kSession.insert(this.warning);

        // Ako želiš "stalno slušanje", može i:
        // new Thread(kSession::fireUntilHalt).start();
    }


    @PreDestroy
    public void destroy() {
        if (this.kSession != null) this.kSession.dispose();
    }


    @PostMapping("/analyzeLongTerm")
    public synchronized Warning analyzeLongTerm(@RequestBody AirPollutionEvent event) {
        long ts = System.currentTimeMillis();  // realtime test; bez ručnog timestamp-a

        // debug log *posle* definicije ts
        System.out.println(event);
        System.out.println("REQ pm25=" + event.getPm25() + ", ts=" + ts + " windSpeed: " + event.getWindSpeed());

        // ubaci događaj u ISTU sesiju
        this.kSession.insert(new AirPollutionEvent(event.getPm25(), event.getPm10(), event.getNo2(),event.getWindSpeed(), ts, event.getUserEmail()));

        // okini pravila
        this.kSession.fireAllRules();

        // snapshot shared statusa
        Warning w = new Warning();
        w.setContent(warning.getContent());

        if (w.getContent() != null){
            User user = userRepository.findByEmail(event.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + event.getUserEmail()));
            w.setUser(user);
            w.setTimestamp(LocalDateTime.now());
            warningRepository.save(w);
        }
        return w;
    }
}
