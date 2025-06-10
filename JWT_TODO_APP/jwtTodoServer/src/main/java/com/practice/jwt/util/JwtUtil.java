package com.practice.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;

    // 토큰 유효시간 설정
    private final long accessTokenValidity = 1000L * 60 * 30; // 30분
    private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7; // 7일

    public JwtUtil(@Value("${jwt.secret}") String secretKey) { this.key = Keys.hmacShaKeyFor(secretKey.getBytes()); }

    // Access Token 생성
    public String generateAccessToken(Long memberNo, String email) {
        return Jwts.builder()
                // 토큰 제목 설정 - 토큰의 용도 명시
                .setSubject("AccessToken")
                // 클레임(Claim) 추가 - 토큰에 포함될 사용자 정보(memberNo, email) 추가
                .claim("memberNo", memberNo)
                .claim("email", email)
                // 발행 시간 설정
                .setIssuedAt(new Date())
                // 토큰 유효 기간 설정
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                // 서명 추가 - HS256 알고리즘을 사용한 토큰 서명
                .signWith(key, SignatureAlgorithm.HS256)
                // 최종적으로 JWT 문자열 생성 (토큰 생성)
                .compact();
    }
    
    // Refresh Token 생성
    public String generateRefreshToken(Long memberNo, String email) {
        return Jwts.builder()
                .setSubject("RefreshToken")
                .claim("memberNo", memberNo)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    // 토큰에서 Claim 추출
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                // 토큰 서명 검증한 후,
                .setSigningKey(key)
                // JWT 파서 생성
                .build()
                // 토큰 파싱 및 검증 (서명 유효성, 토큰 만료 여부, 토큰 형식의 정확성)
                .parseClaimsJws(token)
                // 검증된 토큰에서 Claims(페이로드) 추출
                .getBody();
        
        /* 반환되는 Claims 예시
        {
            "sub": "AccessToken",
            "memberNo": 1,
            "email": "user01@kh.or.kr",
            "iat": 1623456789,
            "exp": 1623460389
        } 
         */
    }

    // 유효한 토큰인지 검증
    // 1. 토큰의 서명이 유효하지 않음 - SignatureException
    // 2. 만료 시간 경과 (exp) - ExpiredJwtException
    // 3. 잘못된 형식 - MalformedJwtException
    // 4. 기타 JWT 관련 오류 (ex. 지원하지 않는 JWT - UnsupportedJwtException)
    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.debug("토큰의 서명이 유효하지 않습니다." +
                    "\n예외 {}", e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            log.debug("만료 시간이 경과되었습니다." +
                    "\n예외 {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            log.debug("토큰 형식이 잘못되었습니다." +
                    "\n예외 {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            log.debug("지원하지 않는 JWT 형식입니다." +
                    "\n예외 {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.debug("예외 {}", e.getMessage());
            return false;
        }
    }

    // 토큰에서 이메일 추출
    public String getEmail(String token) { return getClaims(token).get("email", String.class); }
    
    // 토큰에서 회원번호 추출
    public Long getMemberNo(String token) {
        try {
            return getClaims(token).get("memberNo", Long.class);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰이라도 claims 유효
            return e.getClaims().get("memberNo", Long.class);
        }
    }

    // 토큰 만료일자 추출
    public Date getExpirationDate(String token) { return getClaims(token).getExpiration(); }
}
