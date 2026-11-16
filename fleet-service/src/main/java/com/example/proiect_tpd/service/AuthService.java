package com.example.proiect_tpd.service;

import com.example.proiect_tpd.dto.AuthResponseDto;
import com.example.proiect_tpd.dto.LoginRequestDto;
import com.example.proiect_tpd.dto.RegisterRequestDto;
import com.example.proiect_tpd.model.User;
import com.example.proiect_tpd.repository.UserRepository;
import com.example.proiect_tpd.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
                }
        User user = new User(
                request.fullName(),
                request.email(),
                passwordEncoder.encode(request.password()),
                "ROLE_USER"
        );
        
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDto(jwtToken);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        // Folosim request.email() și request.password()
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        
        User user = userRepository.findByEmail(request.email()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        
        return new AuthResponseDto(jwtToken);
    }
}