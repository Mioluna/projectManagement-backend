package com.backend.projeyonetimi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC ENDPOINTS
                        .requestMatchers("/auth/login", "/auth/register").permitAll()
                        .requestMatchers("/auth/validate").permitAll()

                        // 🔵 PROJECT ENDPOINTS
                        // Only MANAGER can create/update/delete/assign
                        .requestMatchers(HttpMethod.POST, "/api/project/create").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/project/**/update").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/project/**/delete").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/project/**/assign").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/project/**/unassign").hasRole("MANAGER")

                        // Employee OR Manager can VIEW projects
                        .requestMatchers(HttpMethod.GET, "/api/project/**").hasAnyRole("EMPLOYEE", "MANAGER")

                        // 🔵 EMPLOYEE ENDPOINTS
                        // Only MANAGER can create/delete/update employees
                        .requestMatchers(HttpMethod.POST, "/api/employee/create").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/employee/**/delete").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/employee/**/update").hasRole("MANAGER")

                        // Employees can view themselves + managers can view everyone
                        .requestMatchers(HttpMethod.GET, "/api/employee/**").hasAnyRole("EMPLOYEE", "MANAGER")
                        // ANY OTHER ENDPOINT MUST BE AUTHENTICATED
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
