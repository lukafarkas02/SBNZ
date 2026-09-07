package com.ftn.controller;

import com.ftn.dto.ActivityDiagnosisResponse;
import com.ftn.dto.BackwardActivityRequest;
import com.ftn.service.BackwardActivityService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backward")
@CrossOrigin(origins = "http://localhost:4200")
public class BackwardController {

    private final BackwardActivityService backwardActivityService;

    public BackwardController(
            BackwardActivityService backwardActivityService
    ) {
        this.backwardActivityService = backwardActivityService;
    }

    @PostMapping("/activity")
    public ResponseEntity<ActivityDiagnosisResponse> diagnoseActivity(
            @RequestBody BackwardActivityRequest request
    ) {
        return ResponseEntity.ok(
                backwardActivityService.diagnose(request)
        );
    }
}