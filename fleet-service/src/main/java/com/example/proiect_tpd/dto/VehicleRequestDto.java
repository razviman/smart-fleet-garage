package com.example.proiect_tpd.dto;

public record VehicleRequestDto(
        Long userId,
        String vin,
        String brand,
        String model,
        String color,
        Integer year,
        String fuelType,
        String engineCode,
        Integer currentMileage
) {
}