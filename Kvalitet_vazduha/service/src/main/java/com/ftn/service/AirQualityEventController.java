package com.ftn.controller;

import com.ftn.model.AirQualityInput;
import com.ftn.model.messages.Warning;
import com.ftn.model.*;
import com.ftn.dto.AirQualityRequestDTO;
import com.ftn.service.WarningRepository;
import com.ftn.service.UserRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/air")
@CrossOrigin(origins = "http://localhost:4200")
public class AirQualityEventController {

    private final KieContainer kieContainer;
    private KieSession kSession;
    private final WarningRepository warningRepository;
    private final UserRepository userRepository;
    private Warning warning;

    public AirQualityEventController(KieContainer kieContainer, WarningRepository warningRepository, UserRepository userRepository) {
        this.kieContainer = kieContainer;
        this.warningRepository = warningRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        this.kSession = kieContainer.newKieSession("k-session-new-cep");
        System.out.println("CEP KieSession initialized for air quality monitoring.");
        this.warning = new Warning();
        this.kSession.insert(this.warning);
    }

    @PreDestroy
    public void destroy() {
        if (this.kSession != null) this.kSession.dispose();
    }

    // @PostMapping("/pm25Increase")
    // public synchronized ResponseEntity<?> processAirQualityInput(@RequestBody AirQualityRequestDTO request) {
    //     // System.out.println("Received new air quality input: " + input.getTimestamp());
    //     System.out.println("REQUEST >> " + request);

    //     User user = userRepository.findByEmail(request.getEmail())
    //             .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));
    //     // ubaci događaj u sesiju
    //     // this.kSession.insert(input);

    //     for(Pollutant p : request.getMeasurement().getPollutants()) {
    //         this.kSession.insert(p);
    //     }

    //     // pokreni pravila
    //     this.kSession.fireAllRules();

    //     // prikupi sve Warning objekte koji su eventualno generisani
    //     List<Warning> warnings = new ArrayList<>();
    //     for (Object o : this.kSession.getObjects()) {
    //         if (o instanceof Warning) {
    //             Warning warning = (Warning) o;
    //             warning.setUser(user);
    //             warningRepository.save(warning);
    //             warnings.add(warning);
    //         }
    //     }

    //     if (warnings.isEmpty()) {
    //         return ResponseEntity.ok("No sudden increase detected.");
    //     }

    //     return ResponseEntity.ok(warnings);
    // }

    @PostMapping("/pm25Increase")
    public synchronized Warning processAirQualityInput(@RequestBody AirPollutionEvent event) {
        // System.out.println("Received new air quality input: " + input.getTimestamp());
        // System.out.println("REQUEST >> " + request);

        // ubaci događaj u sesiju
        // this.kSession.insert(input);

        // for(Pollutant p : request.getMeasurement().getPollutants()) {
        //     this.kSession.insert(p);
        // }

        long ts = System.currentTimeMillis();  // realtime test; bez ručnog timestamp-a

        // debug log *posle* definicije ts
        System.out.println(event);
        System.out.println("REQ pm25=" + event.getPm25() + ", ts=" + ts + " windSpeed: " + event.getWindSpeed());

        // ubaci događaj u ISTU sesiju
        this.kSession.insert(new AirPollutionEvent(event.getPm25(), event.getPm10(), event.getNo2(),event.getWindSpeed(), ts, event.getUserEmail()));

        // pokreni pravila
        this.kSession.fireAllRules();
        
        User user = userRepository.findByEmail(event.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + event.getUserEmail()));
        
        // prikupi sve Warning objekte koji su eventualno generisani
        // List<Warning> warnings = new ArrayList<>();
        // for (Object o : this.kSession.getObjects()) {
        //     if (o instanceof Warning) {
        //         Warning warning = (Warning) o;
        //         warning.setUser(user);
        //         warningRepository.save(warning);
        //         warnings.add(warning);
        //     }
        // }

        // if (warnings.isEmpty()) {
        //     return ResponseEntity.ok("No sudden increase detected.");
        // }

        // return ResponseEntity.ok(warnings);

        if(warning.getContent() != null){
            Warning w = new Warning();
            w.setContent(warning.getContent());
            w.setTimestamp(LocalDateTime.now());
            w.setUser(user);

            warningRepository.save(w);

            return w;
        }
        else{
            return null;
        }
    }
}
