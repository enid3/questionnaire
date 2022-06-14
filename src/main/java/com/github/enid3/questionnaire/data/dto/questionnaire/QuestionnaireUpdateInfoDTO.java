package com.github.enid3.questionnaire.data.dto.questionnaire;

import com.github.enid3.questionnaire.data.dto.questionnaire.validation.QuestionnaireConstraints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireUpdateInfoDTO {
    @NotEmpty(message = "{questionnaire.validation-message.name-blank}")
    @Size(min = QuestionnaireConstraints.MIN_NAME_SIZE,
            max = QuestionnaireConstraints.MAX_NAME_SIZE,
            message = "{questionnaire.validation-message.name-size}")
    private String name;

    @Size(min = QuestionnaireConstraints.MIN_DESCRIPTION_SIZE,
            max = QuestionnaireConstraints.MAX_DESCRIPTION_SIZE,
            message = "{questionnaire.validation-message.description-size}")
    private String description;
}
