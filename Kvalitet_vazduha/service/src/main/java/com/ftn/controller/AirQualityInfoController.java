package com.ftn.controller;

import com.ftn.dto.AirQualityEvaluationResponseDTO;
import com.ftn.dto.AirQualityRequestDTO;
import com.ftn.service.AirQualityEvaluationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/air-quality")
@CrossOrigin(origins = "http://localhost:4200")
public class AirQualityInfoController {

    private final AirQualityEvaluationService airQualityEvaluationService;

    public AirQualityInfoController(
            AirQualityEvaluationService airQualityEvaluationService
    ) {
        this.airQualityEvaluationService = airQualityEvaluationService;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<AirQualityEvaluationResponseDTO> evaluate(
            @RequestBody AirQualityRequestDTO request
    ) {
        AirQualityEvaluationResponseDTO response =
                airQualityEvaluationService.evaluate(request);

        return ResponseEntity.ok(response);
    }
}