package com.NBE_4_5_2.Team5.domain.user.entity;


import jakarta.persistence.*;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "member")
public class User {

    @Id
    private String id;  // user-UUID


    private String username;

    private String email;

    private String nickname;

    private String address;

    private String profileUrl;

    private int role; // 0: admin , 1: 일반 유저

    private boolean blocked;
    private int blockedCount;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public User(String id, String username, String email, String nickname, String address, String profileUrl,
                int role, boolean blocked, int blockedCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;  //새로운 UUID 할당 UUID.randomUUID().toString();
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
