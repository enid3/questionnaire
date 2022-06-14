package com.github.enid3.questionnaire.data.dto.field.validation;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^[^\\n]*", message = "{field.validation-message.label-single-line}")
@Size(min = FieldConstraints.MIN_LABEL_SIZE,
        max = FieldConstraints.MAX_LABEL_SIZE,
        message = "{field.validation-message.label-size}")
public @interface FieldLabel {
}
