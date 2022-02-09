package com.github.enid3.questionnaire.data.dto.user.auth;

import com.github.enid3.questionnaire.data.dto.user.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
	@NotNull(message = "{user.validation-message.email-not-empty}")
	@Email
	private String email;

	@NotNull(message = "{user.validation-message.pass-not-empty}")
	@Password
    private String password;
}
