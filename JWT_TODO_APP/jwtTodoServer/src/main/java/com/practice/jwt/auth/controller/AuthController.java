package com.practice.jwt.auth.controller;

import com.practice.jwt.auth.dto.LoginRequest;
import com.practice.jwt.auth.dto.LoginResponse;
import com.practice.jwt.auth.service.AuthService;
import com.practice.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
// -> 이미 WebConfig에서 전역적으로 처리해주고 있기 때문에 작성해주지 않아도 됨
public class AuthController {

    // 의존성 주입(DI)
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // JSON 형식으로 비동기 요청을 하기 때문에, @ModelAttribute가 아닌, @RequestBody 활용
        // email, password (LoginRequest) -> accessToken, nickname (LoginResponse)
        
        // 1. 로그인 처리 및 토큰 생성 (서비스에서 Access + Refresh 생성 및 DB 저장)
        Map<String, Object> map = authService.login(request);
        LoginResponse loginResponse = (LoginResponse) map.get("loginResponse");
        
        // 2. Refresh Token 쿠키로 전달(보안상 절대 Body에 보내면 안됨(XSS 공격에 취약)
        
        // 3. Access Token은 본문으로 반환 (localStorage에 저장될 수 있도록)
        
        return null;
    }
    
}
