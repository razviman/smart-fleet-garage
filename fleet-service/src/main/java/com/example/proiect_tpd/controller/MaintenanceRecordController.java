package com.example.proiect_tpd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proiect_tpd.dto.MaintenanceRecordRequestDto;
import com.example.proiect_tpd.dto.MaintenanceRecordResponseDto;
import com.example.proiect_tpd.service.MaintenanceRecordService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController 
@RequestMapping ("/api/maintenance-records")
@SecurityRequirement (name = "bearerAuth")
public class MaintenanceRecordController {

    private final MaintenanceRecordService maintenanceRecordService;
    
    public MaintenanceRecordController(MaintenanceRecordService maintenanceRecordService) {
        this.maintenanceRecordService = maintenanceRecordService;
    }


    @PostMapping
    public ResponseEntity<MaintenanceRecordResponseDto> createMaintenanceRecord(
        @RequestBody MaintenanceRecordRequestDto request) {
        MaintenanceRecordResponseDto response = maintenanceRecordService.createMaintenanceRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
