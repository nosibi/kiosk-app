package com.example.kiosk.member.repository;

import com.example.kiosk.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsMemberByPhoneNumber(String phoneNumber);
    Member findMemberByPhoneNumber(String phoneNumber);
}
