package com.NBE_4_5_2.Team5.domain.user.controller;

import com.NBE_4_5_2.Team5.domain.user.dto.LoginUserDto;
import com.NBE_4_5_2.Team5.domain.user.dto.LoginUserForm;
import com.NBE_4_5_2.Team5.domain.user.dto.SignUpUserForm;
import com.NBE_4_5_2.Team5.domain.user.dto.UserDto;
import com.NBE_4_5_2.Team5.domain.user.entity.User;
import com.NBE_4_5_2.Team5.domain.user.service.UserService;
import com.NBE_4_5_2.Team5.global.Rq;
import com.NBE_4_5_2.Team5.global.dto.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Rq rq;

    @PostMapping("/signup")
    public RsData<UserDto> signup(@RequestBody @Valid SignUpUserForm userForm) {

        User user = userService.signup(userForm.username(), userForm.password(), userForm.email(),
                userForm.nickname(), userForm.address(), userForm.profileUrl());

        return new RsData<>(
                "201-1",
                "회원 가입이 완료되었습니다.",
                new UserDto(user)
        );
    }

    @PostMapping("/login")
    public RsData<LoginUserDto> login(@RequestBody @Valid LoginUserForm userForm) {

        User user = userService.processUserAuthentication(userForm.username(), userForm.password());

        String accessToken = userService.generateAccessToken(user);
        rq.addCookie("accessToken", accessToken);
        rq.addCookie("refreshToken", user.getRefreshToken());

        return new RsData<>(
                "200-1",
                "%s님 환영합니다.".formatted(user.getNickname()),
                new LoginUserDto(
                        accessToken,
                        user.getRefreshToken(),
                        new UserDto(user)
                )
        );
    }

    @PostMapping("/logout")
    public RsData<Void> logout() {

        User userIdentity = rq.getUserIdentity();
        User user = rq.getRealActor(userIdentity);

        userService.logout(user);
        rq.removeCookie("accessToken");
        rq.removeCookie("refreshToken");

        return new RsData<>("200-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/me")
    public RsData<UserDto> me() {

        User userIdentity = rq.getUserIdentity();
        User user = rq.getRealActor(userIdentity);

        return new RsData<>(
                "200-1",
                "내 정보 조회가 완료되었습니다.",
                new UserDto(user)
        );
    }

}
