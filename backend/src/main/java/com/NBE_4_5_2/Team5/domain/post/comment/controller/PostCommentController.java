package com.NBE_4_5_2.Team5.domain.post.comment.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NBE_4_5_2.Team5.domain.post.comment.service.CommentService;
import com.NBE_4_5_2.Team5.domain.user.dto.UserDto;
import com.NBE_4_5_2.Team5.global.dto.RsData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostCommentController {

	private final CommentService commentService;

	public record WriteCommentResBody(String comment, UserDto author) {
	}

	public record WriteCommentReqBody(String comment) {
	}

	@PostMapping("/{post-id}/comments")
	public RsData<WriteCommentResBody> writeComment(@PathVariable(name = "post-id") String postId,
		@RequestBody WriteCommentReqBody body) {

		CommentDto commentDto = commentService.writeComment(postId, body);

	}
}
