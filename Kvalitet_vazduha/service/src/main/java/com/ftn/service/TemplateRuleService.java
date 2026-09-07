package com.ftn.service;

import com.ftn.dto.PollutantClassificationResult;
import com.ftn.dto.PollutantValueDTO;
import com.ftn.dto.ThresholdRuleDTO;
import com.ftn.model.Measurement;
import com.ftn.model.Pollutant;
import com.ftn.model.PollutantType;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TemplateRuleService {

    // Ključ je naziv PollutantType enum vrednosti (npr. "PM2_5") - koristi
    // se direktno u DRL uslovu preko template supstitucije.
    private final Map<String, List<ThresholdRuleDTO>> thresholdsByPollutant = new ConcurrentHashMap<>();

    private volatile KieContainer kieContainer;
    private volatile String generatedDrlText = "";

    @PostConstruct
    public void init() {
        thresholdsByPollutant.put("PM2_5", Arrays.asList(
                row("GOOD", -1, 15),
                row("MODERATE", 15, 35),
                row("POOR", 35, 55),
                row("HAZARDOUS", 55, 999999)
        ));
        thresholdsByPollutant.put("PM10", Arrays.asList(
                row("GOOD", -1, 25),
                row("MODERATE", 25, 50),
                row("POOR", 50, 100),
                row("HAZARDOUS", 100, 999999)
        ));
        thresholdsByPollutant.put("NO2", Arrays.asList(
                row("GOOD", -1, 40),
                row("MODERATE", 40, 90),
                row("POOR", 90, 180),
                row("HAZARDOUS", 180, 999999)
        ));
        thresholdsByPollutant.put("O3", Arrays.asList(
                row("GOOD", -1, 100),
                row("MODERATE", 100, 140),
                row("POOR", 140, 180),
                row("HAZARDOUS", 180, 999999)
        ));
        thresholdsByPollutant.put("CO2", Arrays.asList(
                row("GOOD", -1, 600),
                row("MODERATE", 600, 1000),
                row("POOR", 1000, 2000),
                row("HAZARDOUS", 2000, 999999)
        ));

        rebuildKieContainer();
    }

    private ThresholdRuleDTO row(String category, double min, double max) {
        ThresholdRuleDTO dto = new ThresholdRuleDTO();
        dto.setCategory(category);
        dto.setMinValue(min);
        dto.setMaxValue(max);
        return dto;
    }

    public synchronized void updateThresholds(
            String pollutantType,
            List<ThresholdRuleDTO> rules
    ) {
        String normalizedPollutantType = PollutantType
                .valueOf(pollutantType.trim().toUpperCase())
                .name();

        thresholdsByPollutant.put(normalizedPollutantType, rules);
        rebuildKieContainer();
    }

    private void rebuildKieContainer() {
        List<Map<String, Object>> rows = new ArrayList<>();

        for (Map.Entry<String, List<ThresholdRuleDTO>> entry : thresholdsByPollutant.entrySet()) {
            String pollutantType = entry.getKey();
            for (ThresholdRuleDTO rule : entry.getValue()) {
                Map<String, Object> row = new HashMap<>();
                row.put("pollutantType", pollutantType);
                row.put("category", rule.getCategory());
                row.put("minValue", rule.getMinValue());
                row.put("maxValue", rule.getMaxValue());
                rows.add(row);
            }
        }

        ObjectDataCompiler compiler = new ObjectDataCompiler();
        InputStream templateStream = getClass().getResourceAsStream(
                "/rules/airPollutantsTemplate/air-pollutants-template.drl");

        String drlText = compiler.compile(rows, templateStream);
        this.generatedDrlText = drlText;

        System.out.println(">> Template: rebuild - generisano " + rows.size() + " pravila.");

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/templateRules/generated-thresholds.drl", drlText);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs);
        kieBuilder.buildAll();

        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Greška pri kompajliranju template pravila: "
                    + results.getMessages());
        }

        KieModule kieModule = kieBuilder.getKieModule();
        this.kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
    }

    public KieSession newSession() {
        return kieContainer.newKieSession();
    }

    public String getGeneratedDrlText() {
        return generatedDrlText;
    }

    public List<Map<String, Object>> getAllThresholdsFlat() {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Map.Entry<String, List<ThresholdRuleDTO>> entry : thresholdsByPollutant.entrySet()) {
            for (ThresholdRuleDTO rule : entry.getValue()) {
                Map<String, Object> row = new HashMap<>();
                row.put("pollutantType", entry.getKey());
                row.put("category", rule.getCategory());
                row.put("minValue", rule.getMinValue());
                row.put("maxValue", rule.getMaxValue());
                rows.add(row);
            }
        }
        return rows;
    }

    public List<ThresholdRuleDTO> getThresholdsForPollutant(String pollutantType) {
        String normalizedPollutantType = PollutantType
                .valueOf(pollutantType.trim().toUpperCase())
                .name();

        return thresholdsByPollutant.getOrDefault(
                normalizedPollutantType,
                new ArrayList<>()
        );
    }

    public Set<String> getPollutantTypes() {
        return thresholdsByPollutant.keySet();
    }

    /**
     * Klasifikuje listu izmerenih vrednosti polutanata koristeći trenutno
     * generisana template pravila. Sva Drools logika (kreiranje sesije,
     * insert/fireAllRules/dispose) je ovde - kontroler samo prosleđuje
     * DTO listu i vraća rezultat.
     */
    public List<PollutantClassificationResult> classify(List<PollutantValueDTO> pollutants) {
        KieSession session = newSession();

        Measurement measurement = new Measurement();
        List<Pollutant> pollutantList = new ArrayList<>();

        for (PollutantValueDTO dto : pollutants) {
            PollutantType pollutantType = PollutantType.valueOf(
                    dto.getPollutantType().trim().toUpperCase());

            Pollutant p = new Pollutant(
                    pollutantType,
                    dto.getValue(),
                    null,
                    measurement
            );

            pollutantList.add(p);
        }

        measurement.setPollutants(pollutantList);

        session.insert(measurement);

        for (Pollutant pollutant : pollutantList) {
            session.insert(pollutant);
        }

        session.fireAllRules();
        session.dispose();

        List<PollutantClassificationResult> response = new ArrayList<>();

        for (Pollutant p : pollutantList) {
            response.add(new PollutantClassificationResult(
                    p.getPollutantType().name(),
                    p.getValue(),
                    p.getStatus() != null ? p.getStatus().name() : "N/A"
            ));
        }

        return response;
    }
}