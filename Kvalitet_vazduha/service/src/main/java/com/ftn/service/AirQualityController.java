package com.ftn.service;

import com.ftn.model.AirQualityStatus;
import com.ftn.model.AirQualityCategory;
import com.ftn.model.AirPollutionEvent;
import com.ftn.util.KnowledgeSessionHelper;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/air")
public class AirQualityController {

    private final KieContainer kieContainer;

    public AirQualityController(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    @PostMapping("/analyze")
    public AirQualityStatus analyze(@RequestBody AirPollutionEvent event) {
        KieSession kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "k-session-cep");

        AirQualityStatus status = new AirQualityStatus();
        kSession.insert(status);
        kSession.insert(new AirPollutionEvent(event.getPm25(), event.getPm10(), event.getNo2(), System.currentTimeMillis()));

        kSession.fireAllRules();
        kSession.dispose();

        return status;
    }

    @PostMapping("/analyzeLongTerm")
    public AirQualityStatus analyzeLongTerm(@RequestBody AirPollutionEvent event) {
        KieSession kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "k-session-cep");

        AirQualityStatus status = new AirQualityStatus();
        kSession.insert(status);
        kSession.insert(new AirPollutionEvent(event.getPm25(), event.getPm10(), event.getNo2(), System.currentTimeMillis()));

        kSession.fireAllRules();
        kSession.dispose();

        return status;
    }
}
