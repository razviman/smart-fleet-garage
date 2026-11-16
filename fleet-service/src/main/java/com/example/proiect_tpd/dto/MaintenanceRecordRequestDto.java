package com.example.proiect_tpd.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MaintenanceRecordRequestDto(
    String status,
    String title,
    String description,
    String aiDiagnosticNote,
    String urgencyLevel,
    Integer mileageAtMaintenance,
    BigDecimal cost,
    LocalDateTime date,
    Long vehicleId
) {
}