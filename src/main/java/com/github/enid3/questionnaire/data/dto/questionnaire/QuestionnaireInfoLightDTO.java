package com.github.enid3.questionnaire.data.dto.questionnaire;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireInfoLightDTO extends IdentifiableDTO {
    private String name;
    private String description;
}
