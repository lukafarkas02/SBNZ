package com.ftn.controller;

import com.ftn.dto.CepMeasurementRequest;
import com.ftn.model.messages.Warning;
import com.ftn.service.CepStreamService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cep")
@CrossOrigin(origins = "http://localhost:4200")
public class CepStreamController {

    private final CepStreamService cepStreamService;

    public CepStreamController(CepStreamService cepStreamService) {
        this.cepStreamService = cepStreamService;
    }

    @PostMapping("/stream-measurement")
    public List<Warning> streamMeasurement(@RequestBody CepMeasurementRequest req) {
        return cepStreamService.streamMeasurement(req);
    }
}