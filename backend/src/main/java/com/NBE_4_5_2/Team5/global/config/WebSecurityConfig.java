//package com.NBE_4_5_2.Team5.global.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
//                .headers(headers -> headers
//                        .frameOptions(frameOptions -> frameOptions.sameOrigin())) // iframe 사용 허용
//                .formLogin(withDefaults()) // 기본 폼 로그인 설정 사용
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/chat/**").hasRole("USER") // /chat/** 경로는 USER 역할이 있어야 접근 가능
//                        .anyRequest().permitAll()); // 나머지 요청은 모두 허용
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("123").password("{noop}1234").roles("USER").build());
//        manager.createUser(User.withUsername("234").password("{noop}1234").roles("USER").build());
//        manager.createUser(User.withUsername("345").password("{noop}1234").roles("GUEST").build());
//        return manager;
//    }
//
//}