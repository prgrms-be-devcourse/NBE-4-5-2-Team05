package com.NBE_4_5_2.Team5.domain.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NBE_4_5_2.Team5.domain.admin.entity.Notice;

public interface NoticePostRepository extends JpaRepository<Notice, String> {
}
