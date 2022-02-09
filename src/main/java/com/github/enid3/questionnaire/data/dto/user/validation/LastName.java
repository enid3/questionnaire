package com.github.enid3.questionnaire.data.dto.user.validation;

import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.github.enid3.questionnaire.data.dto.user.validation.UserValidationConstraints.MAX_SECOND_NAME_LENGTH;
import static com.github.enid3.questionnaire.data.dto.user.validation.UserValidationConstraints.MIN_SECOND_NAME_LENGTH;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Size(min = MIN_SECOND_NAME_LENGTH,
        max = MAX_SECOND_NAME_LENGTH,
        message = "{user.validation-message.second-name-size}")
public @interface LastName {
}
