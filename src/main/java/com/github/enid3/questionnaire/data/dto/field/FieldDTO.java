package com.github.enid3.questionnaire.data.dto.field;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import com.github.enid3.questionnaire.data.dto.field.validation.FieldLabel;
import com.github.enid3.questionnaire.data.entity.Field;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class FieldDTO extends IdentifiableDTO {
    @NotBlank(message = "{field.validation-message.label-blank}")
    @FieldLabel
    private String label;

    @NotNull(message = "{field.validation-message.type-not-empty}")
    private Field.Type type;

    @NotNull(message = "{field.validation-message.required-not-empty}")
    private Boolean isRequired = true;

    @NotNull(message = "{field.validation-message.active-not-empty}")
    private Boolean isActive = true;

    @NotNull(message = "{field.validation-message.options-not-empty}")
    private Set<String> options = new HashSet<>();
}
