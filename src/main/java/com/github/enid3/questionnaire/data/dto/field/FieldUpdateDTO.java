package com.github.enid3.questionnaire.data.dto.field;

import com.github.enid3.questionnaire.data.dto.field.validation.FieldLabel;
import com.github.enid3.questionnaire.data.entity.Field;
import lombok.Data;

import java.util.Set;

@Data
public class FieldUpdateDTO {
    @FieldLabel
    private String label;

    private Field.Type type;
    private Boolean isRequired;
    private Boolean isActive;
    private Set<String> options;
}
