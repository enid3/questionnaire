package com.github.enid3.questionnaire.data.dto.field;

import com.github.enid3.questionnaire.data.dto.field.validation.FieldConstraints;
import com.github.enid3.questionnaire.data.entity.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldUpdateDTO {
    @Pattern(regexp = "^[^\\n]*", message = "{field.validation-message.label-single-line}")
    @Size(min = FieldConstraints.MIN_LABEL_SIZE,
            max = FieldConstraints.MAX_LABEL_SIZE,
            message = "{field.validation-message.label-size}")
    private String label;

    private Field.Type type;
    private Boolean isRequired;
    private Boolean isActive;
    private Set<String> options;
}
