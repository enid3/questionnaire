package com.github.enid3.questionnaire.data.dto.questionnaire;

import com.github.enid3.questionnaire.data.dto.questionnaire.validation.QuestionnaireConstraints;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireCreateDTO {
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
