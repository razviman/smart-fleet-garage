package com.example.proiect_tpd.repository;

import com.example.proiect_tpd.model.User;
import com.example.proiect_tpd.model.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByVin(String vin);
    List<Vehicle> findByUser(User user);
}
