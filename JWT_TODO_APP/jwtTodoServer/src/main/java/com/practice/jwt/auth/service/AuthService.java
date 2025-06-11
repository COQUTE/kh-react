package com.practice.jwt.auth.service;

import com.practice.jwt.auth.dto.LoginRequest;

import java.util.Map;

public interface AuthService {
    
    Map<String, Object> login(LoginRequest request);
}
