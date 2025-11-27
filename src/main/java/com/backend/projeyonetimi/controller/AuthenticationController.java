package com.backend.projeyonetimi.controller;

import com.backend.projeyonetimi.dtos.AuthenticationResponse;
import com.backend.projeyonetimi.dtos.LoginRequest;
import com.backend.projeyonetimi.model.Employee;
import com.backend.projeyonetimi.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import com.backend.projeyonetimi.repository.EmployeeRepository;
import com.backend.projeyonetimi.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    EmployeeRepository employeeRepository,
                                    JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.employeeRepository = employeeRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }

    @GetMapping("/validate")
    public AuthenticationResponse validateToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid token");
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthenticationResponse(
                token,
                employee.getRole(),
                employee.getId(),
                employee.getFirstName() + " " + employee.getLastName()
        );
    }
}


