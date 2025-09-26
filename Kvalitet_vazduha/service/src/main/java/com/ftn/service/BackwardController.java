// src/main/java/com/ftn/sbnz/api/BackwardController.java
package com.ftn.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.model.BackwardEvalRequest;
import com.ftn.model.AirPollutionEvent;
import com.ftn.model.AirQualityStatus;
import com.ftn.model.UserProfile;

@RestController
@RequestMapping("/api/backward")
public class BackwardController {

    private final KieContainer kieContainer;

    public BackwardController(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<AirQualityStatus> evaluate(@RequestBody BackwardEvalRequest req) {

        KieSession ks = kieContainer.newKieSession("backward-ksession"); // ili ime ako koristiš named-session iz kmodule.xml

        try {
            // 1) Činjenice
            AirPollutionEvent e = new AirPollutionEvent();
            e.setPm25(req.pm25);
            e.setPm10(req.pm10);
            e.setNo2(req.no2);
            e.setO3(req.o3);
            e.setWindSpeed(req.windSpeed);
            e.setPrecipitation(req.precipitation);
            ks.insert(e);

            UserProfile up = new UserProfile();
            up.setUserType(req.userCategory);
            ks.insert(up);

            AirQualityStatus status = new AirQualityStatus();
            ks.insert(status);

            // 2) Pali pravila
            ks.fireAllRules();

            return ResponseEntity.ok(status);
        } finally {
            ks.dispose();
        }
    }
}
