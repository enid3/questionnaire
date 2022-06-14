package com.github.enid3.questionnaire.data.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.github.enid3.questionnaire.data.dto.user.validation.UserConstraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    @Size(min = MIN_FIRST_NAME_LENGTH,
            max = MAX_FIRST_NAME_LENGTH,
            message = "{user.validation-message.first-name-size}")
    private String firstName;
    @Size(min = MIN_LAST_NAME_LENGTH,
            max = MAX_LAST_NAME_LENGTH,
            message = "{user.validation-message.second-name-size}")
    private String lastName;

    @Email(message = "{user.validation-message.email}")
    private String email;

    @Pattern(regexp="(^$|[0-9]{10})", message = "{user.validation-message.phone-number}")
    private String phoneNumber;
}
