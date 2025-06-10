package com.practice.jwt.member.repository;

import com.practice.jwt.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> { // Entity + PK 데이터 타입
    
    // Email 회원 찾기
    
    // 비밀번호 변경하기
    
}
