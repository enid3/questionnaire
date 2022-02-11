package com.github.enid3.questionnaire.data.dto.user.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.github.enid3.questionnaire.data.dto.user.validation.UserValidationConstraints.MAX_PASS_LENGTH;
import static com.github.enid3.questionnaire.data.dto.user.validation.UserValidationConstraints.MIN_PASS_LENGTH;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
	@NotNull(message = "{user.validation-message.email-not-empty}")
	@Email
	private String email;

	@NotNull(message = "{user.validation-message.pass-not-empty}")
	@Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH, message = "{user.validation-message.pass-size}")
    private String password;
}
