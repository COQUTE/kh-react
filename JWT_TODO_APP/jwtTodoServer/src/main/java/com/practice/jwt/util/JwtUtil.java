package com.practice.jwt.util;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {
    private final Key key;

    // 토큰 유효시간 설정
    private final long accessTokenValidity = 1000L * 60 * 30; // 30분
    private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7; // 7일

    public JwtUtil(@Value("${jwt.secret}") String secretKey) { this.key = Keys.hmacShaKeyFor(secretKey.getBytes()); }

    // Access Token 생성
    
    // Refresh Token 생성
    
    // 토큰에서 Claim 추출

    // 유효한 토큰인지 검증(
    // 1. 서명 불일치
    // 2. 만료 시간 경과 (exp)
    // 3. 구조적 오류
    // 4. 기타 JWT 관련 오류)

    // 토큰에서 이메일 추출

    // 토큰에서 회원번호 추출

    // 토큰 만료일자 추출
}
