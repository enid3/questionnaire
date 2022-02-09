package com.github.enid3.questionnaire.data.dto.user;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import com.github.enid3.questionnaire.data.dto.user.validation.*;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO extends IdentifiableDTO {
    @FirstName
    private String firstName;

    @LastName
    private String lastName;

    @NotNull(message = "{auth.validation-message.email-not-empty}")
    @Email
    private String email;

    @NotNull(message = "{auth.validation-message.pass-not-empty}")
    @Password
    private String password;

    @PhoneNumber
    private String phoneNumber;
}
