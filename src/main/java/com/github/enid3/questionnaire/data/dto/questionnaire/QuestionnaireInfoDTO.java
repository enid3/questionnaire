package com.github.enid3.questionnaire.data.dto.questionnaire;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import com.github.enid3.questionnaire.data.dto.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireInfoDTO extends IdentifiableDTO {
    private String name;
    private String description;
    private UserInfoDTO userInfo;
}
