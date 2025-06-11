package com.practice.jwt.auth.service;

import com.practice.jwt.auth.dto.LoginRequest;
import com.practice.jwt.auth.dto.LoginResponse;
import com.practice.jwt.auth.entity.RefreshToken;
import com.practice.jwt.auth.repository.RefreshTokenRepository;
import com.practice.jwt.member.entity.Member;
import com.practice.jwt.member.repository.MemberRepository;
import com.practice.jwt.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bcrypt;
    private final JwtUtil jwtUtil;
    
    @Override
    public Map<String, Object> login(LoginRequest request) {
        
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
        // Optional 사용
        // 1. .orElse(defaultValue) - 값이 없으면 기본값 반환
        // 2. .orElseThrow(() -> new Exception()) - 값이 없으면 예외 발생
        // 3. .ifPresent(val -> {}) - 값이 있으면 람다 실행
        
        if (!bcrypt.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        
        // accessToken & refreshToken 받아오기
        String accessToken = jwtUtil.generateAccessToken(member.getMemberNo(), member.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(member.getMemberNo(), member.getEmail());
        
        // 토큰 만료 날짜 받아오기
        Date expirationDate = jwtUtil.getExpirationDate(refreshToken);
        LocalDateTime localExpirationDate = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // Date는 레거시 클래스, 여러 설계상의 문제가 있음
        // LocalDateTime이 더 안전하고 사용하기 편리
        // 시간대 처리가 필요한 경우에 더 명확하게 처리 가능
        // JWT 토큰의 만료 시간을 다루거나, 데이터베이스의 타임스탬프를 처리할 때 이러한 변환이 필요
        
        // 1. 영속성 컨텍스트 내 기존 토큰 조회 (읽기용)
        RefreshToken tokenEntity = refreshTokenRepository.findById(member.getMemberNo()).orElse(null);
        
        if (tokenEntity == null) {
            // 2. RefreshToken이 없으면, 새로 생성해서 저장 (persist)
            tokenEntity = RefreshToken.builder().memberNo(member.getMemberNo()).token(refreshToken)
                    .expirationDate(localExpirationDate).build();
            
            refreshTokenRepository.save(tokenEntity);
        } else {
            // 3. 있으면 업데이트 (영속 상태이므로 트랜잭션 커밋 시 자동 반영)
            tokenEntity.update(refreshToken, localExpirationDate);
            // save() 호출하지 않아도 됨, 이미 영속 상태라 변경 감지됨
        }
        
        Map<String, Object> map = new HashMap<>();
        map.put("refreshToken", refreshToken);
        map.put("loginResponse", new LoginResponse(accessToken, member.getName()));
        
        return map;
    }
    
    @Override
    public Member findMemberByEmail(String userEmail) {
        return memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
    }
    
    @Override
    public String findRefreshToken(long memberNo) {
        return refreshTokenRepository
                .findById(memberNo) // Optional<RefreshToken> 객체를 반환함 (해당 회원의 토큰이 있을 수도, 없을 수도 있음)
                .map(RefreshToken::getToken) // Optional<RefreshToken>을 Optional<String>으로 변환 (토큰 문자열만 추출)
                .orElse(null); // 값이 없을 경우 null 반환
    }
    
    @Override
    public LocalDateTime findRefreshTokenExpiration(Long memberNo) {
        return refreshTokenRepository
                .findById(memberNo)
                .map(RefreshToken::getExpirationDate)
                .orElse(null);
    }
}
