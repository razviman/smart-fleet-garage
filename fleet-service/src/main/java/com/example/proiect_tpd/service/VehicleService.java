package com.example.proiect_tpd.service;

import com.example.proiect_tpd.dto.VehicleRequestDto;
import com.example.proiect_tpd.dto.VehicleResponseDto;
import com.example.proiect_tpd.model.User;
import com.example.proiect_tpd.model.Vehicle;
import com.example.proiect_tpd.repository.UserRepository;
import com.example.proiect_tpd.repository.VehicleRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {


    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public VehicleResponseDto createVehicle(VehicleRequestDto request, User owner) {

        // verify if the VIN already exists in the database
        if (vehicleRepository.existsByVin(request.vin())) {
            throw new IllegalArgumentException("There is already a vehicle with this VIN: " + request.vin());
        }

        // verify if the user exists in the database

        // create a new Vehicle entity and set its properties from the request DTO
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(request.vin());
        vehicle.setBrand(request.brand());
        vehicle.setModel(request.model());
        vehicle.setColor(request.color());
        vehicle.setYear(request.year());
        vehicle.setFuelType(request.fuelType());
        vehicle.setEngineCode(request.engineCode());
        vehicle.setCurrentMileage(request.currentMileage());
        vehicle.setUser(owner);

        // save to the database
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // map to response DTO and return
        return mapToResponseDto(savedVehicle);
    }


    @Transactional
    public List<VehicleResponseDto> getVehiclesByUser(User user) {
        List<Vehicle> vehicles = vehicleRepository.findByUser(user);
        return vehicles.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // method to map Vehicle entity to VehicleResponseDto
    private VehicleResponseDto mapToResponseDto(Vehicle vehicle) {
        return new VehicleResponseDto(
                vehicle.getId(),
                vehicle.getVin(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getColor(),
                vehicle.getYear(),
                vehicle.getFuelType(),
                vehicle.getEngineCode(),
                vehicle.getCurrentMileage(),
                vehicle.getUser().getId(),
                vehicle.getUser().getFullName()
        );
    }
}
