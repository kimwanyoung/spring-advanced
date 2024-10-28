package org.example.expert.domain.todo.entity;

import java.util.ArrayList;
import java.util.List;

import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.user.entity.User;
import org.springframework.util.ObjectUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "todos")
public class Todo extends Timestamped {

	@OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
	private final List<Comment> comments = new ArrayList<>();
	@OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST)
	private final List<Manager> managers = new ArrayList<>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String contents;
	private String weather;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Todo(String title, String contents, String weather, User user) {
		this.title = title;
		this.contents = contents;
		this.weather = weather;
		this.user = user;
		this.managers.add(new Manager(user, this));
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public void validateIsAuthor(User author) {
		if (user == null || !user.isSameUser(author)) {
			throw new InvalidRequestException("해당 일정을 만든 유저가 유효하지 않습니다.");
		}
	}

	public void validateManagerExists(Manager manager) {
		if (!ObjectUtils.nullSafeEquals(id, manager.getTodo().getId())) {
			throw new InvalidRequestException("해당 일정에 등록된 담당자가 아닙니다.");
		}
	}

	public void validateNotSelfAssignedAsManager(User managerUser) {
		if (user.isSameUser(managerUser)) {
			throw new InvalidRequestException("해당 일정을 만든 유저가 유효하지 않습니다.");
		}
	}

	public void validateHasAuthor() {
		if (user == null) {
			throw new InvalidRequestException("담당자를 등록하려고 하는 유저가 일정을 만든 유저가 유효하지 않습니다.");
		}
	}
}
