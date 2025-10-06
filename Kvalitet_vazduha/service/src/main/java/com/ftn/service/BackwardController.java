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
import com.ftn.model.*;
import com.ftn.dto.*;
import com.ftn.service.UserRepository;

@RestController
@RequestMapping("/api/backward")
@CrossOrigin(origins = "http://localhost:4200")
public class BackwardController {

    private final KieContainer kieContainer;

    private final UserRepository userRepository;

    public BackwardController(KieContainer kieContainer, UserRepository userRepository) {
        this.kieContainer = kieContainer;
        this.userRepository = userRepository;
    }

    // @PostMapping("/evaluate")
    // public ResponseEntity<AirQualityStatus> evaluate(@RequestBody BackwardEvalRequest req) {

    //     KieSession ks = kieContainer.newKieSession("backward-ksession"); // ili ime ako koristiš named-session iz kmodule.xml

    //     try {
    //         // 1) Činjenice
    //         System.out.println(req);
    //         AirPollutionEvent e = new AirPollutionEvent();
    //         e.setPm25(req.pm25);
    //         e.setPm10(req.pm10);
    //         e.setNo2(req.no2);
    //         e.setO3(req.o3);
    //         e.setWindSpeed(req.windSpeed);
    //         e.setPrecipitation(req.precipitation);
    //         ks.insert(e);

    //         UserProfile up = new UserProfile();
    //         up.setUserType(req.userCategory);
    //         ks.insert(up);

    //         AirQualityStatus status = new AirQualityStatus();
    //         ks.insert(status);

    //         // 2) Pali pravila
    //         ks.fireAllRules();

    //         return ResponseEntity.ok(status);
    //     } finally {
    //         ks.dispose();
    //     }
    // }

    @PostMapping("/evaluate")
    public ResponseEntity<?> evaluate(@RequestBody BackwardQualityRequest req) {

        KieSession ks = kieContainer.newKieSession("backward-hazardous-quality-ksession"); // ili ime ako koristiš named-session iz kmodule.xml

        try {
            // 1) Činjenice
            System.out.println(req);
            AirPollutionEvent e = new AirPollutionEvent();
            e.setPm25(req.pm25);
            e.setPm10(req.pm10);
            e.setNo2(req.no2);
            e.setO3(req.o3);
            e.setWindSpeed(req.windSpeed);
            e.setPrecipitation(req.precipitation);
            ks.insert(e);

            User user = userRepository.findByEmail(req.userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + req.userEmail));
            ks.insert(user);

            // AirQualityStatus status = new AirQualityStatus();
            // ks.insert(status)
            BackwardReasoningResult br = new BackwardReasoningResult();
            ks.insert(br);

            // 2) Pali pravila
            ks.fireAllRules();

            return ResponseEntity.ok(br);
        } finally {
            ks.dispose();
        }
    }

    @PostMapping("/mask")
    public ResponseEntity<?> mask(@RequestBody BackwardQualityRequest req) {

        KieSession ks = kieContainer.newKieSession("backward-mask-ksession"); // ili ime ako koristiš named-session iz kmodule.xml

        try {
            // 1) Činjenice
            System.out.println(req);
            AirPollutionEvent e = new AirPollutionEvent();
            e.setPm25(req.pm25);
            e.setPm10(req.pm10);
            e.setNo2(req.no2);
            e.setO3(req.o3);
            e.setWindSpeed(req.windSpeed);
            e.setPrecipitation(req.precipitation);
            ks.insert(e);

            User user = userRepository.findByEmail(req.userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + req.userEmail));
            ks.insert(user);

            // AirQualityStatus status = new AirQualityStatus();
            // ks.insert(status);
            MaskRecommendationResponse mr = new MaskRecommendationResponse();
            ks.insert(mr);

            // 2) Pali pravila
            ks.fireAllRules();

            return ResponseEntity.ok(mr);
        } finally {
            ks.dispose();
        }
    }
}
