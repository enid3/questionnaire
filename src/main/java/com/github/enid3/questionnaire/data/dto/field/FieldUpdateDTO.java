package com.github.enid3.questionnaire.data.dto.field;

import com.github.enid3.questionnaire.data.dto.field.validation.FieldValidationConstraints;
import com.github.enid3.questionnaire.data.entity.Field;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class FieldUpdateDTO {
    @Pattern(regexp = "^[^\\n]*", message = "{field.validation-message.label-single-line}")
    @Size(min = FieldValidationConstraints.MIN_LABEL_SIZE,
            max = FieldValidationConstraints.MAX_LABEL_SIZE,
            message = "{field.validation-message.label-size}")
    private String label;

    private Field.Type type;
    private Boolean isRequired;
    private Boolean isActive;
    private Set<String> options;
}
