package com.example.proiect_tpd.dto;

public record RegisterRequestDto(
        String fullName,
        String email,
        String password
) {}