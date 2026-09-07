package com.ftn.controller;

import com.ftn.dto.AdminMeasurementDTO;
import com.ftn.dto.AdminWarningDTO;
import com.ftn.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/measurements")
    public ResponseEntity<List<AdminMeasurementDTO>> getAllMeasurements() {

        return ResponseEntity.ok(
                adminService.getAllMeasurements()
        );
    }

    @GetMapping("/warnings")
    public ResponseEntity<List<AdminWarningDTO>> getAllWarnings() {

        return ResponseEntity.ok(
                adminService.getAllWarnings()
        );
    }
} 
    
