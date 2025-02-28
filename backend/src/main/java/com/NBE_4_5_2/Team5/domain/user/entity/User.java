package com.NBE_4_5_2.Team5.domain.user.entity;


import jakarta.persistence.*;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name ="member")
public class User {

    @Id
    @Column(length = 255)
    private String id;  // user-UUID

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private String address;

    @Column(name = "profile_url")
    private String profileUrl;

    private int role; // 0: admin , 1: 일반 유저

    private boolean blocked;
    private int blockedCount;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public User(String id, String username, String email, String nickname, String address, String profileUrl, int role, boolean blocked, int blockedCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.address = address;
        this.profileUrl = profileUrl;
        this.role = role;
        this.blocked = blocked;
        this.blockedCount = blockedCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


}
