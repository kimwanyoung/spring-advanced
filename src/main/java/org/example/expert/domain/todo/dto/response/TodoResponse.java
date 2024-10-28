package org.example.expert.domain.todo.dto.response;

import java.time.LocalDateTime;

import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.dto.response.UserResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoResponse {

	private final Long id;
	private final String title;
	private final String contents;
	private final String weather;
	private final UserResponse user;
	private final LocalDateTime createdAt;
	private final LocalDateTime modifiedAt;

	public static TodoResponse from(Todo todo) {
		return new TodoResponse(
			todo.getId(),
			todo.getTitle(),
			todo.getContents(),
			todo.getWeather(),
			new UserResponse(todo.getUser().getId(), todo.getUser().getEmail()),
			todo.getCreatedAt(),
			todo.getModifiedAt()
		);
	}
}
