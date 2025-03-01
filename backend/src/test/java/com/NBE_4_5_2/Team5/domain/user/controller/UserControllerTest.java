package com.NBE_4_5_2.Team5.domain.user.controller;

import com.NBE_4_5_2.Team5.domain.user.entity.User;
import com.NBE_4_5_2.Team5.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    private void checkUser(ResultActions resultActions, User user) throws Exception {
        resultActions
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(user.getId()))
                .andExpect(jsonPath("$.data.username").value(user.getUsername()))
                .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.nickname").value(user.getNickname()))
                .andExpect(jsonPath("$.data.address").value(user.getAddress()))
                .andExpect(jsonPath("$.data.profileUrl").value(user.getProfileUrl()))
                .andExpect(jsonPath("$.data.role").value(user.getRole()))
                .andExpect(jsonPath("$.data.createdAt").value(matchesPattern(user.getCreatedAt().toString().replaceAll("0+$", "") + ".*")))
                .andExpect(jsonPath("$.data.modifiedAt").value(matchesPattern(user.getModifiedAt().toString().replaceAll("0+$", "") + ".*")));
    }

    private ResultActions signupRequest(String username, String password, String email, String nickname,
                                        String address, String profileUrl) throws Exception {
        return mvc
                .perform(
                        post("/api/users/signup")
                                .content("""
                                        {
                                          "username": "%s",
                                          "password": "%s",
                                          "email": "%s",
                                          "nickname": "%s",
                                          "address": "%s",
                                          "profileUrl": "%s"
                                        }
                                        """
                                        .formatted(username, password, email, nickname, address, profileUrl)
                                        .stripIndent())
                                .contentType(
                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());
    }

    @Test
    @DisplayName("회원 가입1")
    void signup1() throws Exception {

        String username = "userNew";
        String password = "new1234@";
        String email = "new@naver.com";
        String nickname = "무명";
        String address = "서울시 강남구";
        String profileUrl = "default_profile.png";

        ResultActions resultActions = signupRequest(username, password, email, nickname, address, profileUrl);

        User user = userRepository.findByUsername(username).get();
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getId()).startsWith("user-");

        resultActions
                .andExpect(status().isCreated())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("signup"))
                .andExpect(jsonPath("$.code").value("201-1"))
                .andExpect(jsonPath("$.message").value("회원 가입이 완료되었습니다."));

        checkUser(resultActions, user);

    }

    @Test
    @DisplayName("회원 가입2 - username 중복")
    void signup2() throws Exception {

        String username = "user1";
        String password = "new1234@";
        String email = "new@naver.com";
        String nickname = "무명";
        String address = "서울시 강남구";
        String profileUrl = "https://example.com/default_profile.png";

        ResultActions resultActions = signupRequest(username, password, email, nickname, address, profileUrl);

        resultActions
                .andExpect(status().isConflict())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("signup"))
                .andExpect(jsonPath("$.code").value("409-1"))
                .andExpect(jsonPath("$.message").value("이미 사용중인 아이디입니다."));

    }

    @Test
    @DisplayName("회원 가입3 - email 중복")
    void signup3() throws Exception {

        String username = "user4";
        String password = "new1234@";
        String email = "user1@gmail.com";
        String nickname = "무명";
        String address = "서울시 강남구";
        String profileUrl = "https://example.com/default_profile.png";

        ResultActions resultActions = signupRequest(username, password, email, nickname, address, profileUrl);

        resultActions
                .andExpect(status().isConflict())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("signup"))
                .andExpect(jsonPath("$.code").value("409-2"))
                .andExpect(jsonPath("$.message").value("이미 사용중인 이메일입니다."));

    }

    @Test
    @DisplayName("회원 가입4 - nickname 중복")
    void signup4() throws Exception {

        String username = "user4";
        String password = "new1234@";
        String email = "user4@gmail.com";
        String nickname = "user1";
        String address = "서울시 강남구";
        String profileUrl = "https://example.com/default_profile.png";

        ResultActions resultActions = signupRequest(username, password, email, nickname, address, profileUrl);

        resultActions
                .andExpect(status().isConflict())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("signup"))
                .andExpect(jsonPath("$.code").value("409-3"))
                .andExpect(jsonPath("$.message").value("이미 사용중인 닉네임입니다."));

    }

    @Test
    @DisplayName("회원 가입5 - 필수 입력 데이터 누락")
    void signup5() throws Exception {

        String username = "";
        String password = "";
        String email = "";
        String nickname = "";
        String address = "서울시 강남구";
        String profileUrl = "https://example.com/default_profile.png";

        ResultActions resultActions = signupRequest(username, password, email, nickname, address, profileUrl);

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("signup"))
                .andExpect(jsonPath("$.code").value("400-1"))
                .andExpect(jsonPath("$.message").value("""
                        email : 이메일은 필수 입력값입니다.
                        nickname : 닉네임은 2~20자 사이여야 합니다.
                        password : 비밀번호는 8~50자 사이여야 합니다.
                        password : 비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.
                        username : 아이디는 4~20자 사이여야 합니다.
                        username : 아이디는 영문과 숫자만 사용할 수 있습니다.
                        """.stripIndent().stripTrailing()));
    }

    @Test
    @DisplayName("회원 가입6 - 잘못된 형식의 데이터 입력")
    void signup6() throws Exception {

        String username = "wrong id"; // 공백 포함
        String password = "wrongpassword"; // 특수문자 미포함
        String email = "wrongemail"; // 이메일 형식 미준수
        String nickname = "i"; // 1자 미만
        String address = "서울시 강남구";
        String profileUrl = "https://example.com/default_profile.png";

        ResultActions resultActions = signupRequest(username, password, email, nickname, address, profileUrl);

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("signup"))
                .andExpect(jsonPath("$.code").value("400-1"))
                .andExpect(jsonPath("$.message").value("""
                        email : 올바른 이메일 형식이 아닙니다.
                        nickname : 닉네임은 2~20자 사이여야 합니다.
                        password : 비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.
                        username : 아이디는 영문과 숫자만 사용할 수 있습니다.
                        """.stripIndent().stripTrailing()));
    }

}