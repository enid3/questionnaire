package com.github.enid3.questionnaire.data.dto.user;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDTO extends IdentifiableDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
