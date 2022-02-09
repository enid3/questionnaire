package com.github.enid3.questionnaire.data.dto.user.validation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@javax.validation.constraints.Email(message = "{user.validation-message.email}")
public @interface Email {
}
