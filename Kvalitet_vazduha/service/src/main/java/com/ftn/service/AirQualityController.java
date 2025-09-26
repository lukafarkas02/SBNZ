package com.ftn.service;

import com.ftn.model.AirQualityStatus;
import com.ftn.model.AirQualityCategory;
import com.ftn.model.AirPollutionEvent;
import com.ftn.util.KnowledgeSessionHelper;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@RestController
@RequestMapping("/api/air")
public class AirQualityController {

    private final KieContainer kieContainer;
    private KieSession kSession;

    private AirQualityStatus sharedStatus;

    public AirQualityController(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    @PostConstruct
    public void init() {
        // koristi istu sesiju (npr. "k-session-cep") kroz ceo život aplikacije
        this.kSession = kieContainer.newKieSession("k-session-cep");

        // ako koristiš status kao fact:
        this.sharedStatus = new AirQualityStatus();
        this.kSession.insert(this.sharedStatus);

        // Ako želiš "stalno slušanje", može i:
        // new Thread(kSession::fireUntilHalt).start();
    }


    @PreDestroy
    public void destroy() {
        if (this.kSession != null) this.kSession.dispose();
    }


    @PostMapping("/analyzeLongTerm")
    public synchronized AirQualityStatus analyzeLongTerm(@RequestBody AirPollutionEvent event) {
        long ts = System.currentTimeMillis();  // realtime test; bez ručnog timestamp-a

        // debug log *posle* definicije ts
        System.out.println(event);
        System.out.println("REQ pm25=" + event.getPm25() + ", ts=" + ts + " windSpeed: " + event.getWindSpeed());

        // ubaci događaj u ISTU sesiju
        this.kSession.insert(new AirPollutionEvent(event.getPm25(), event.getPm10(), event.getNo2(),event.getWindSpeed(), event.getTimestamp()));

        // okini pravila
        this.kSession.fireAllRules();

        // snapshot shared statusa
        AirQualityStatus ret = new AirQualityStatus();
        ret.setCategory(sharedStatus.getCategory());
        ret.setExplanation(sharedStatus.getExplanation());
        ret.setRecommendation(sharedStatus.getRecommendation());
        return ret;
    }
}
