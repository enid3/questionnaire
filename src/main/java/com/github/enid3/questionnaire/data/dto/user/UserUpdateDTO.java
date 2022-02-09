package com.github.enid3.questionnaire.data.dto.user;

import com.github.enid3.questionnaire.data.dto.user.validation.Email;
import com.github.enid3.questionnaire.data.dto.user.validation.FirstName;
import com.github.enid3.questionnaire.data.dto.user.validation.LastName;
import com.github.enid3.questionnaire.data.dto.user.validation.PhoneNumber;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDTO {
    @FirstName
    private String firstName;
    @LastName
    private String lastName;

    @Email
    private String email;

    @PhoneNumber
    private String phoneNumber;
}
