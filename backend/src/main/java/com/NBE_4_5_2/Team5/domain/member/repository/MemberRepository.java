package com.NBE_4_5_2.Team5.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NBE_4_5_2.Team5.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}
