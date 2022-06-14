package com.github.enid3.questionnaire.data.dto.user;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO extends IdentifiableDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
