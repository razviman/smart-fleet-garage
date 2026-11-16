package com.example.proiect_tpd.controller;

import com.example.proiect_tpd.dto.VehicleRequestDto;
import com.example.proiect_tpd.dto.VehicleResponseDto;
import com.example.proiect_tpd.model.User;
import com.example.proiect_tpd.service.VehicleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/vehicles")
@SecurityRequirement (name = "bearerAuth")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@RequestBody VehicleRequestDto request, 
        @AuthenticationPrincipal User currentUser)  {
        VehicleResponseDto response = vehicleService.createVehicle(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getVehiclesByUser(@AuthenticationPrincipal User currentUser) {
        List<VehicleResponseDto> vehicles = vehicleService.getVehiclesByUser(currentUser);
        return ResponseEntity.ok(vehicles);
    }
}
