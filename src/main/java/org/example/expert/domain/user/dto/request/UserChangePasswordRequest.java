package org.example.expert.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequest {

	@NotBlank
	private String oldPassword;
	@NotBlank
	@Size(min = 8, message = "새 비밀번호는 8자 이상이어야 합니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$)", message = "새 비밀번호는 영문 대 소문자, 숫자, 특수문자를 사용해야 합니다.")
	private String newPassword;
}
