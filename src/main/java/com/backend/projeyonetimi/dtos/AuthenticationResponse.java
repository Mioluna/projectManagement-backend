package com.backend.projeyonetimi.dtos;

public class AuthenticationResponse {
    private String token;
    private String role;
    private Integer id;
    private String fullName;


    public AuthenticationResponse(String token, String role, Integer id, String fullName) {
        this.token = token;
        this.role = role;
        this.id = id;
        this.fullName = fullName;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public Integer getId() {
        return Math.toIntExact(id);
    }

    public String getFullName() {
        return fullName;
    }
}
