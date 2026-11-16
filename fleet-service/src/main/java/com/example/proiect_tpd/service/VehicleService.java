package com.example.proiect_tpd.service;

import com.example.proiect_tpd.dto.VehicleRequestDto;
import com.example.proiect_tpd.dto.VehicleResponseDto;
import com.example.proiect_tpd.model.User;
import com.example.proiect_tpd.model.Vehicle;
import com.example.proiect_tpd.repository.UserRepository;
import com.example.proiect_tpd.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {


    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public VehicleResponseDto createVehicle(VehicleRequestDto request) {

        // verify if the VIN already exists in the database
        if (vehicleRepository.existsByVin(request.vin())) {
            throw new IllegalArgumentException("There is already a vehicle with this VIN: " + request.vin());
        }

        // verify if the user exists in the database
        User owner = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + request.userId() + " not found!"));

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
