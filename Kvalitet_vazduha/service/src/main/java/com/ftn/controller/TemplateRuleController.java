package com.ftn.controller;

import com.ftn.dto.PollutantClassificationResult;
import com.ftn.dto.PollutantValueDTO;
import com.ftn.dto.ThresholdRuleDTO;
import com.ftn.service.TemplateRuleService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/template")
@CrossOrigin(origins = "http://localhost:4200")
public class TemplateRuleController {

    private final TemplateRuleService templateRuleService;

    public TemplateRuleController(TemplateRuleService templateRuleService) {
        this.templateRuleService = templateRuleService;
    }

    @GetMapping("/pollutant-types")
    public Set<String> getPollutantTypes() {
        return templateRuleService.getPollutantTypes();
    }

    @GetMapping("/thresholds")
    public List<Map<String, Object>> getAllThresholds() {
        return templateRuleService.getAllThresholdsFlat();
    }

    @GetMapping("/thresholds/{pollutantType}")
    public List<ThresholdRuleDTO> getThresholdsForPollutant(@PathVariable String pollutantType) {
        return templateRuleService.getThresholdsForPollutant(pollutantType);
    }

    @PostMapping("/thresholds/{pollutantType}")
    public Map<String, Object> saveThresholds(
            @PathVariable String pollutantType,
            @RequestBody List<ThresholdRuleDTO> rules
    ) {
        templateRuleService.updateThresholds(pollutantType, rules);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("pollutantType", pollutantType.trim().toUpperCase());
        response.put("ruleCount", rules.size());
        response.put(
                "totalGeneratedRules",
                templateRuleService.getAllThresholdsFlat().size()
        );

        return response;
    }

    @GetMapping("/generated-rules")
    public String getGeneratedRulesText() {
        return templateRuleService.getGeneratedDrlText();
    }

    @PostMapping("/classify")
    public List<PollutantClassificationResult> classify(@RequestBody List<PollutantValueDTO> pollutants) {
        return templateRuleService.classify(pollutants);
    }
}