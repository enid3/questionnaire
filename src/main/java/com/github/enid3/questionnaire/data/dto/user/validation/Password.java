package com.github.enid3.questionnaire.data.dto.user.validation;

import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.github.enid3.questionnaire.data.dto.user.validation.UserValidationConstraints.MAX_PASS_LENGTH;
import static com.github.enid3.questionnaire.data.dto.user.validation.UserValidationConstraints.MIN_PASS_LENGTH;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH, message = "{user.validation-message.pass-size}")
public @interface Password {
}
