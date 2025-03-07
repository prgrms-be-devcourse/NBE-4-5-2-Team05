package com.NBE_4_5_2.Team5.domain.user.service;

import com.NBE_4_5_2.Team5.domain.user.entity.RefreshToken;
import com.NBE_4_5_2.Team5.domain.user.entity.User;
import com.NBE_4_5_2.Team5.domain.user.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String REFRESH_TOKEN_KEY = "refreshToken:";

    private final RedisRepository redisRepository;

    @Value("${custom.refreshToken.expire-seconds}")
    private Long expireSeconds;

    /**
     * RefreshToken을 저장 (expireSeconds 적용)
     */
    public void saveRefreshToken(User user, String refreshToken) {
        RefreshToken token = RefreshToken.builder()
                .refreshToken(generateRefreshTokenKey(refreshToken))
                .userId(user.getId())
                .expiration(expireSeconds)
                .build();
        redisRepository.save(token);

    }

    /**
     * redis에 refreshToken 조회
     */
    public Optional<RefreshToken> getRefreshToken(String refreshToken) {
        String key = generateRefreshTokenKey(refreshToken);
        return redisRepository.findById(key);
    }

    /**
     * redis에 refreshToken 삭제
     */
    public void deleteRefreshToken(String refreshToken) {
        String key = generateRefreshTokenKey(refreshToken);
        redisRepository.deleteById(key);
    }

    /**
     * RefreshToken 키 생성
     *
     * @return redis key
     */
    private String generateRefreshTokenKey(String refreshToken) {
        return REFRESH_TOKEN_KEY + refreshToken;
    }
}
