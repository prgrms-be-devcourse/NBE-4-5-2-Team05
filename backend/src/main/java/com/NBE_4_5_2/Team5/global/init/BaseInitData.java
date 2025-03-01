package com.NBE_4_5_2.Team5.global.init;

import com.NBE_4_5_2.Team5.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final MemberService memberService;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> memberInit();
    }

    @Transactional
    public void memberInit() {

        if (memberService.count() > 0) {
            return;
        }

        memberService.signUp("user1", "user11234", "user1@gmail.com", "user1", "서울시 강남구", "https://example.com/default_profile.png");
        memberService.signUp("user2", "user21234", "user2@gmail.com", "user2", "서울시 강서구", "https://example.com/default_profile.png");
        memberService.signUp("user3", "user31234", "user3@gmail.com", "user3", "서울시 광진구", "https://example.com/default_profile.png");

    }
}