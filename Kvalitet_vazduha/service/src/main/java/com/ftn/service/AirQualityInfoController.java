package com.ftn.controller;

import com.ftn.model.*;
import com.ftn.service.AirQualityInfoRepository;
import com.ftn.service.AirQualityInputRepository;
import com.ftn.service.UserRepository;
import org.kie.api.runtime.KieSession;
import org.kie.api.KieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ftn.model.AirQualityInput;
import com.ftn.dto.AirQualityRequestDTO;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/air-quality")
@CrossOrigin(origins = "http://localhost:4200")
public class AirQualityInfoController {

    @Autowired
    private AirQualityInfoRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AirQualityInputRepository inputRepository;

    @PostMapping("/evaluate")
    public AirQualityInfo evaluate(@RequestBody AirQualityRequestDTO request) {
        System.out.println("REQUEST >> " + request);
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));
        
        AirQualityInput input = new AirQualityInput();
        input.setUser(user);

        Measurement measurement = request.getMeasurement();
        if (measurement != null && measurement.getPollutants() != null) {
            for (Pollutant p : measurement.getPollutants()) {
                p.setMeasurement(measurement);
            }
        }
        input.setPollutantMeasurment(request.getMeasurement());
        input.setTimestamp(LocalDateTime.now());

        inputRepository.save(input);

        KieServices ks = KieServices.Factory.get();
        KieSession kieSession = ks.getKieClasspathContainer().newKieSession("basic-ksession");

        kieSession.insert(input);
        kieSession.fireAllRules();

        AirQualityInfo result = null;
        for (Object o : kieSession.getObjects()) {
            if (o instanceof AirQualityInfo) {
                result = (AirQualityInfo) o;
                break;
            }
        }

        kieSession.dispose();

        System.out.println("RESULT>> " + result);
        if (result != null) {
            System.out.println("RESULT>> " + result);
            repository.save(result);
        }

        return result;
    }
}
