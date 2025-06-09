package com.practice.jwt.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // bcrypt 사용을 위한 Bean 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CORS(Cross-Origin Resource Sharing) 기본 설정을 활성화
        http.cors(Customizer.withDefaults())
                // CSRF(Cross-Site Request Forgery) 보호를 비활성화
                .csrf(csrf -> csrf.disable())
                // OPTIONS HTTP 메서드로 들어오는 모든 요청(`/**`)을 허용
                // 주로 브라우저의 사전 요청(pre-flight request)을 처리하기 위함
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // `/auth/`로 시작하는 모든 경로는 인증 없이 접근 가능
                        .requestMatchers("/auth/**").permitAll()
                        // 위에서 설정한 경로 외의 모든 요청은 인증이 필요
                        // 로그인한 사용자만 접근 가능
                        .anyRequest().authenticated());

        return http.build();
    }
}
