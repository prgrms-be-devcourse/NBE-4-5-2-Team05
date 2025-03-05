package com.NBE_4_5_2.Team5.domain.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.NBE_4_5_2.Team5.TestConfig;
import com.NBE_4_5_2.Team5.domain.admin.entity.NoticePost;
import com.NBE_4_5_2.Team5.domain.admin.repository.NoticePostRepository;
import com.NBE_4_5_2.Team5.domain.admin.service.AdminService;
import com.NBE_4_5_2.Team5.domain.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private NoticePostRepository noticePostRepository;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void writeNotice() throws Exception {
		//given
		User admin = adminService.signUpAdmin("admin", "password", "admin@admin.com");

		Cookie[] cookies = mockMvc.perform(post("/api/users/login")
				.content("""
					{
						"username": "admin",
						"password": "password"
					}""")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn().getResponse().getCookies();

		Assertions.assertThat(Arrays.stream(cookies).map(Cookie::getName))
			.contains("accessToken", "refreshToken");

		// when
		ResultActions perform = mockMvc.perform(post("/api/admin/notices")
			.content("""
				{
					"title": "공지 제목",
					"content": "공지 내용"
				}""")
			.contentType("application/json")
			.characterEncoding("utf-8")
			.cookie(cookies[0], cookies[1]));

		String id = objectMapper.readTree(perform.andReturn().getResponse().getContentAsString())
			.get("data").get("id").asText();

		NoticePost noticePost = noticePostRepository.findById(id).get();

		// then
		perform
			.andExpect(status().isOk())
			.andExpect(handler().handlerType(AdminController.class))
			.andExpect(handler().methodName("writeNotice"))
			.andExpect(jsonPath("$.code").value("200-1"))
			.andExpect(jsonPath("$.message").value("공지사항 등록 성공."))
			.andExpect(jsonPath("$.data.id").value(noticePost.getId()))
			.andExpect(jsonPath("$.data.title").value(noticePost.getTitle()))
			.andExpect(jsonPath("$.data.content").value(noticePost.getContent()))
			.andExpect(jsonPath("$.data.admin.id").value(admin.getId()));
	}
}