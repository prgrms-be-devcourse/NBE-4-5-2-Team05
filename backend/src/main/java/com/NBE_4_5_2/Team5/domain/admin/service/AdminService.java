package com.NBE_4_5_2.Team5.domain.admin.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NBE_4_5_2.Team5.domain.admin.dto.BanListDto;
import com.NBE_4_5_2.Team5.domain.admin.dto.NoticeResBody;
import com.NBE_4_5_2.Team5.domain.admin.entity.BanList;
import com.NBE_4_5_2.Team5.domain.admin.entity.NoticePost;
import com.NBE_4_5_2.Team5.domain.admin.repository.BanListRepository;
import com.NBE_4_5_2.Team5.domain.admin.repository.NoticePostRepository;
import com.NBE_4_5_2.Team5.domain.user.entity.Role;
import com.NBE_4_5_2.Team5.domain.user.entity.User;
import com.NBE_4_5_2.Team5.domain.user.repository.UserRepository;
import com.NBE_4_5_2.Team5.global.exception.ServiceException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
	private static final int BAN_DURATION_WEIGHT = 7;
	private final BanListRepository banListRepository;
	private final UserRepository userRepository;
	private final NoticePostRepository noticePostRepository;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public User signUpAdmin(String username, String password, String email) {
		User admin = new User(username, passwordEncoder.encode(password), email, "admin", "addr", "profile",
			Role.ADMIN);
		admin.setRefreshToken(UUID.randomUUID().toString());

		return userRepository.save(admin);
	}

	public NoticeResBody writeNotice(@NotEmpty String title, @NotEmpty String content) {
		User admin = getUser();

		isAdmin(admin);

		NoticePost noticePost = NoticePost.builder().title(title).content(content)
			.admin(admin).build();

		NoticePost saved = noticePostRepository.save(noticePost);

		return NoticeResBody.of(saved);

	}

	private void isAdmin(User admin) {
		if (!admin.getRole().equals(Role.ADMIN)) {
			throw new ServiceException(HttpStatus.BAD_REQUEST.toString(), "관리자만 작성할 수 있는 글입니다.");
		}
	}

	private User getUser() {
		return userRepository.findAllByRole(Role.ADMIN).get(0);
	}

	public BanListDto banUser(String userId, @NotEmpty String reason) {
		User loggedInUser = getUser();
		User bannedUser = userRepository.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("id가 %s인 user를 찾을 수 없습니다.".formatted(userId)));

		isAdmin(loggedInUser);

		BanList banList = new BanList(reason, bannedUser, LocalDateTime.now()
			.plusDays((long)(bannedUser.getBlockedCount() + 1) * BAN_DURATION_WEIGHT));

		BanList saved = banListRepository.save(banList);

		bannedUser.ban();

		return new BanListDto(saved);
	}
}
