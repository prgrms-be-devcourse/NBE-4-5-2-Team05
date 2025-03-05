package com.NBE_4_5_2.Team5.domain.post.comment.service;

import org.springframework.stereotype.Service;

import com.NBE_4_5_2.Team5.domain.post.comment.controller.PostCommentController;
import com.NBE_4_5_2.Team5.domain.post.comment.dto.CommentDto;
import com.NBE_4_5_2.Team5.domain.post.comment.entity.Comment;
import com.NBE_4_5_2.Team5.domain.post.comment.repository.CommentRepository;
import com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost;
import com.NBE_4_5_2.Team5.domain.post.post.repository.ProductPostRepository;
import com.NBE_4_5_2.Team5.domain.user.entity.User;
import com.NBE_4_5_2.Team5.domain.user.service.UserService;
import com.NBE_4_5_2.Team5.global.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final UserService userService;
	private final ProductPostRepository productPostRepository;
	private final CommentRepository commentRepository;

	public CommentDto writeComment(String postId, PostCommentController.WriteCommentReqBody body) {
		User loggedInUser = getUser();

		ProductPost productPost = productPostRepository.findById(postId)
			.orElseThrow(() -> new ServiceException("400-1", "id가 %s인 product post는 없습니다.".formatted(postId)));

		Comment comment = new Comment(body.comment(), productPost, loggedInUser);

		Comment saved = commentRepository.save(comment);
		return CommentDto.of(saved);
	}

	private User getUser() {
		return userService.getUserIdentity();
	}
}
