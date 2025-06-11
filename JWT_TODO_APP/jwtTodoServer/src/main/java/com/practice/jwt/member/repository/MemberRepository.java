package com.practice.jwt.member.repository;

import com.practice.jwt.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> { // Entity + PK 데이터 타입
    
    // Email 회원 찾기
    Optional<Member> findByEmail(String email);
    // Optional은 null을 보다 안전하게 다루기 위한 컨테이너 객체
    
    // 비밀번호 변경하기
    
}
