package com.github.enid3.questionnaire.data.dto.user.auth;

import com.github.enid3.questionnaire.data.dto.user.validation.Password;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordDTO {
    @NotNull(message = "{user.validation-message.old-pass-not-empty}")
    @Password
    private String oldPassword;

    @NotNull(message = "{user.validation-message.pass-not-empty}")
    @Password
    private String newPassword;
}
