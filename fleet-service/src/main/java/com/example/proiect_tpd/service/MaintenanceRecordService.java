package com.example.proiect_tpd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proiect_tpd.dto.MaintenanceRecordRequestDto;
import com.example.proiect_tpd.dto.MaintenanceRecordResponseDto;
import com.example.proiect_tpd.model.MaintenanceRecord;
import com.example.proiect_tpd.repository.MaintenanceRecordRepository;
import com.example.proiect_tpd.repository.VehicleRepository;

@Service
public class MaintenanceRecordService {

    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceRecordService(MaintenanceRecordRepository maintenanceRecordRepository, VehicleRepository vehicleRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional 
    public MaintenanceRecordResponseDto createMaintenanceRecord(MaintenanceRecordRequestDto request) {
        // Implement the logic to create a maintenance record
        // This is a placeholder implementation
        MaintenanceRecord maintenanceRecord = new MaintenanceRecord();
        maintenanceRecord.setStatus(request.status());
        maintenanceRecord.setTitle(request.title());
        maintenanceRecord.setDescription(request.description());
        maintenanceRecord.setAiDiagnosticNote(request.aiDiagnosticNote());
        maintenanceRecord.setUrgencyLevel(request.urgencyLevel());
        maintenanceRecord.setMileageAtMaintenance(request.mileageAtMaintenance());
        maintenanceRecord.setCost(request.cost());
        maintenanceRecord.setServiceDate(request.date());
        maintenanceRecord.setVehicle(vehicleRepository.findById(request.vehicleId()).orElse(null));

        MaintenanceRecord savedRecord = maintenanceRecordRepository.save(maintenanceRecord);

        return mapToResponseDto(savedRecord);
    }


    private MaintenanceRecordResponseDto mapToResponseDto(MaintenanceRecord maintenanceRecord) {
        return new MaintenanceRecordResponseDto(
            maintenanceRecord.getId(),
            maintenanceRecord.getStatus(),
            maintenanceRecord.getTitle(),
            maintenanceRecord.getDescription(),
            maintenanceRecord.getAiDiagnosticNote(),
            maintenanceRecord.getUrgencyLevel(),
            maintenanceRecord.getMileageAtMaintenance(),
            maintenanceRecord.getCost(),
            maintenanceRecord.getServiceDate(),
            maintenanceRecord.getVehicle().getId()
        );
    }
    
}
