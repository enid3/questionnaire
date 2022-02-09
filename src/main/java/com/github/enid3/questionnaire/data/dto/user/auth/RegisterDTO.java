package com.github.enid3.questionnaire.data.dto.user.auth;

import com.github.enid3.questionnaire.data.dto.user.validation.*;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterDTO {
    @FirstName
    private String firstName;

    @LastName
    private String lastName;

    @NotNull(message = "{user.validation-message.email-not-empty}")
    @Email
    private String email;

    @NotNull(message = "{user.validation-message.pass-not-empty}")
    @Password
    private String password;

    @PhoneNumber
    private String phoneNumber;
}
