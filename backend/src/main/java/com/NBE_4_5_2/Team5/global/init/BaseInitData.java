package com.NBE_4_5_2.Team5.global.init;

import com.NBE_4_5_2.Team5.domain.user.entity.User;
import com.NBE_4_5_2.Team5.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final UserRepository userRepository;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> userInit();
    }

    @Transactional
    public void userInit() {
        if (userRepository.count() > 0) {
            return; // 기존 데이터가 있으면 초기화하지 않음
        }

        // 새로운 UUID 생성
        String userId = "a26f722-1aa6-4784-a8b8-acde4b8a30b1";

        // UUID가 기존 데이터베이스에 있는지 확인
        if (!userRepository.existsById(userId)) {
            User user = new User(
                    userId,
                    "testuser",
                    "test@example.com",
                    "테스트유저",
                    "서울특별시 강남구",
                    "https://example.com/profile.jpg",
                    1, // role 추가
                    false,
                    0,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            // 저장 전에 중복 검사
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isEmpty()) {
                userRepository.save(user);
                System.out.println("초기 유저 데이터 삽입 완료: " + user.getUsername());
            } else {
                System.out.println("이미 존재하는 유저입니다: " + user.getEmail());
            }
        }
    }
}
