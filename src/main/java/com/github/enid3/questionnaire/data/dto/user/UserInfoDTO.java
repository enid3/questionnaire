package com.github.enid3.questionnaire.data.dto.user;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO extends IdentifiableDTO {
    private String firstName;
    private String lastName;
}
