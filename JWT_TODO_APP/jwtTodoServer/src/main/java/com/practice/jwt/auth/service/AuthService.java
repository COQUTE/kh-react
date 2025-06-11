package com.practice.jwt.auth.service;

import com.practice.jwt.auth.dto.LoginRequest;
import com.practice.jwt.member.entity.Member;

import java.time.LocalDateTime;
import java.util.Map;

public interface AuthService {
    
    Map<String, Object> login(LoginRequest request);
    
    Member findMemberByEmail(String userEmail);
    
    String findRefreshToken(long memberNo);
    
    LocalDateTime findRefreshTokenExpiration(Long memberNo);
}
