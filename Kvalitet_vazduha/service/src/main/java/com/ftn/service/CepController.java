package com.ftn.controller;

import com.ftn.model.*;
import com.ftn.model.messages.GeneralMessage;
import org.kie.api.runtime.KieSession;
import org.kie.api.KieServices;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cep")
@CrossOrigin(origins = "http://localhost:4200")
public class CepController {

    @PostMapping("/test-sudden-increase")
    public String testSuddenIncrease(@RequestBody List<AirQualityInput> inputs) {
        KieServices ks = KieServices.Factory.get();
        KieSession kieSession = ks.getKieClasspathContainer().newKieSession("cep-ksession");

        // Ubacujemo sve događaje
        for (AirQualityInput input : inputs) {
            kieSession.insert(input);
        }

        int fired = kieSession.fireAllRules();
        kieSession.dispose();

        return "CEP Rules triggered: " + fired;
    }
}
