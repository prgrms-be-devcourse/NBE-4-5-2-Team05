package com.NBE_4_5_2.Team5.domain.user.service;

import com.NBE_4_5_2.Team5.domain.user.dto.UserDto;
import com.NBE_4_5_2.Team5.domain.user.entity.User;
import com.NBE_4_5_2.Team5.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    //프로필 조회
    public UserDto getUserProfile(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        return new UserDto(
                user.getId(),
                user.getRole(),
                user.getUsername(),
                user.getEmail(),
                user.getNickname(),
                user.getAddress(),
                user.getProfileUrl(),
                user.isBlocked(),
                user.getBlockedCount(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );

    }

}
