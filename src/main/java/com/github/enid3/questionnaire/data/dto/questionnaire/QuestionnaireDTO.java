package com.github.enid3.questionnaire.data.dto.questionnaire;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.questionnaire.validation.QuestionnaireConstraints;
import com.github.enid3.questionnaire.data.dto.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireDTO extends IdentifiableDTO {
    @NotEmpty(message = "{questionnaire.validation-message.name-blank}")
    @Size(min = QuestionnaireConstraints.MIN_NAME_SIZE,
            max = QuestionnaireConstraints.MAX_NAME_SIZE,
            message = "{questionnaire.validation-message.name-size}")
    private String name;

    @Size(min = QuestionnaireConstraints.MIN_DESCRIPTION_SIZE,
            max = QuestionnaireConstraints.MAX_DESCRIPTION_SIZE,
            message = "{questionnaire.validation-message.description-size}")
    private String description;

    private UserInfoDTO userInfo;

    List<FieldDTO> fields;
}
