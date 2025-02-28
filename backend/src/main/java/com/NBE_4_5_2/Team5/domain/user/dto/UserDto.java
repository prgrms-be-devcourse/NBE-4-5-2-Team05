package com.NBE_4_5_2.Team5.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter@AllArgsConstructor
public class UserDto {
    private String id;
    private int role; // 0 : admin 1: user
    private String username;
    private String email;
    private String nickname;
    private String address;
    private String profileUrl;
    private boolean blocked;
    private int blockedCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
