package com.ftn.controller;

import com.ftn.model.messages.Warning;
import com.ftn.service.WarningService;
import org.springframework.web.bind.annotation.*;
import com.ftn.model.*;

import java.util.List;

@RestController
@RequestMapping("/api/warnings")
@CrossOrigin(origins = "http://localhost:4200")
public class WarningController {

    private final WarningService warningService;

    public WarningController(WarningService warningService) {
        this.warningService = warningService;
    }

    @GetMapping("/{email}")
    public List<Warning> getWarningsForUser(@PathVariable String email) {
        return warningService.getWarningsByUserEmail(email);
    }
}
