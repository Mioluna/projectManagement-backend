package com.backend.projeyonetimi.service;

import com.backend.projeyonetimi.dtos.AuthenticationResponse;
import com.backend.projeyonetimi.dtos.LoginRequest;
import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.repository.EmployeeRepository;
import com.backend.projeyonetimi.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtService jwtService,
                                 EmployeeRepository employeeRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.employeeRepository = employeeRepository;
    }

    public AuthenticationResponse login(LoginRequest request) {

        // 1. Authenticate user (email + password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Load employee from database
        Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Generate JWT token
        String token = jwtService.generateToken(employee);

        // 4. Return token + role + id
        String fullName = employee.getFirstName() + " " + employee.getLastName();

        return new AuthenticationResponse(
                token,
                employee.getRole(),
                employee.getId(),
                fullName
        );

    }
}
