package com.NBE_4_5_2.Team5.domain.user.repository;

import com.NBE_4_5_2.Team5.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String > {
    Optional<Users> findByUsername(String username);
}