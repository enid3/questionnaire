package com.github.enid3.questionnaire.data.dto.user.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.github.enid3.questionnaire.data.dto.user.validation.UserConstraints.MAX_PASS_LENGTH;
import static com.github.enid3.questionnaire.data.dto.user.validation.UserConstraints.MIN_PASS_LENGTH;

@Data
public class ChangePasswordDTO {
    @NotNull(message = "{user.validation-message.old-pass-not-empty}")
    @Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH, message = "{user.validation-message.pass-size}")
    private String oldPassword;

    @NotNull(message = "{user.validation-message.pass-not-empty}")
    @Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH, message = "{user.validation-message.pass-size}")
    private String newPassword;
}
