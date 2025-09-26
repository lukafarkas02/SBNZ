package com.ftn.service;

import com.ftn.util.KnowledgeSessionHelper;
import com.ftn.model.AirPollutionEvent;
import com.ftn.model.AirQualityStatus;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TestCEP {
    public static void main(String[] args) {
        // KieContainer kc = KnowledgeSessionHelper.createRuleBase();
        // KieSession kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kc, "k-session-cep");

        // AirQualityStatus status = new AirQualityStatus();
        // kSession.insert(status);

        // long now = System.currentTimeMillis();

        // Simulacija događaja u vremenu (oduzimamo minute u milisekundama)
        // kSession.insert(new AirPollutionEvent(60, 20, 30, now - 25 * 60 * 1000));
        // kSession.insert(new AirPollutionEvent(70, 30, 40, now - 15 * 60 * 1000));
        // kSession.insert(new AirPollutionEvent(80, 25, 35, now - 5 * 60 * 1000));

        // kSession.insert(new AirPollutionEvent(70, 30, 40, now - 5 * 60 * 60 * 1000));  // pre 5h
        // kSession.insert(new AirPollutionEvent(75, 32, 42, now - 4 * 60 * 60 * 1000));  // pre 4h
        // kSession.insert(new AirPollutionEvent(80, 35, 45, now - 3 * 60 * 60 * 1000));  // pre 3h
        // kSession.insert(new AirPollutionEvent(85, 38, 48, now - 2 * 60 * 60 * 1000));  // pre 2h
        // kSession.insert(new AirPollutionEvent(90, 40, 50, now - 1 * 60 * 60 * 1000));  // pre 1h
        // kSession.insert(new AirPollutionEvent(95, 42, 52, now - 10 * 60 * 1000));      // pre 10 min

        // kSession.fireAllRules();

        // System.out.println("Konačna kategorija: " + status.getCategory());
        // System.out.println("Preporuka: " + status.getRecommendation());

        // kSession.dispose();
    }
}
