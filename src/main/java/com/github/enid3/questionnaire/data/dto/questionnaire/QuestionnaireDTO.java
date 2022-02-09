package com.github.enid3.questionnaire.data.dto.questionnaire;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionnaireDTO {
    List<FieldDTO> fields;
}
