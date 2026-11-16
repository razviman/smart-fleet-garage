package com.example.proiect_tpd.repository;

import com.example.proiect_tpd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
