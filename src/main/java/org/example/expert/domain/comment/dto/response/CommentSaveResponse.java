package org.example.expert.domain.comment.dto.response;

import org.example.expert.domain.user.dto.response.UserResponse;

import lombok.Getter;

@Getter
public class CommentSaveResponse {

	private final Long id;
	private final String contents;
	private final UserResponse user;

	public CommentSaveResponse(Long id, String contents, UserResponse user) {
		this.id = id;
		this.contents = contents;
		this.user = user;
	}
}
