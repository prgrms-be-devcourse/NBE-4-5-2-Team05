package com.NBE_4_5_2.Team5.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import com.NBE_4_5_2.Team5.global.dto.RsData;
import com.NBE_4_5_2.Team5.global.standard.util.Ut;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final CustomAuthenticationFilter customAuthenticationFilter;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/h2-console/**", "/error")
				.permitAll()
				.requestMatchers("/api/posts/**", "/api/comments/**", "/api/payments/**", "/api/admin/**",
					"/api/chat/**", "/api/chatting/**")
				.permitAll()
				.requestMatchers("/api/users/login", "/api/users/signup", "/api/users/refresh")
				.permitAll()
				.anyRequest()
				.authenticated()
			)
			.headers((headers) -> headers
				.addHeaderWriter(new XFrameOptionsHeaderWriter(
					XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
			.csrf(csrf -> csrf.disable())
			.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(
				exceptionHandling -> exceptionHandling
					.authenticationEntryPoint(
						(request, response, authException) -> {
							log.error("Authentication exception", authException);
							response.setContentType("application/json;charset=UTF-8");
							response.setStatus(401);
							response.getWriter().write(
								Ut.Json.toString(
									new RsData("401-1", "잘못된 인증키입니다.")
								)
							);
						}
					)
					.accessDeniedHandler(
						(request, response, authException) -> {
							response.setContentType("application/json;charset=UTF-8");
							response.setStatus(403);
							response.getWriter().write(
								Ut.Json.toString(
									new RsData("403-1", "접근 권한이 없습니다.")
								)
							);
						}
					)

			);

		return http.build();
	}

}

