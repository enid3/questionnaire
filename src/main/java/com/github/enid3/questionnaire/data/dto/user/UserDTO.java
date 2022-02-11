package com.github.enid3.questionnaire.data.dto.user;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.github.enid3.questionnaire.data.dto.user.validation.UserValidationConstraints.*;

@Data
public class UserDTO extends IdentifiableDTO {
    @Size(min = MIN_FIRST_NAME_LENGTH,
            max = MAX_FIRST_NAME_LENGTH,
            message = "{user.validation-message.first-name-size}")
    private String firstName;

    @Size(min = MIN_SECOND_NAME_LENGTH,
            max = MAX_SECOND_NAME_LENGTH,
            message = "{user.validation-message.second-name-size}")
    private String lastName;

    @NotNull(message = "{auth.validation-message.email-not-empty}")
    @Email(message = "{user.validation-message.email}")
    private String email;

    @NotNull(message = "{auth.validation-message.pass-not-empty}")
    @Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH, message = "{user.validation-message.pass-size}")
    private String password;

    @Pattern(regexp="(^$|[0-9]{10})", message = "{user.validation-message.phone-number}")
    private String phoneNumber;
}
