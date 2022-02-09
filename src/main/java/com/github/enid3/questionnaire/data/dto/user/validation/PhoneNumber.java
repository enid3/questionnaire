package com.github.enid3.questionnaire.data.dto.user.validation;


import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp="(^$|[0-9]{10})", message = "{user.validation-message.phone-number}")
public @interface PhoneNumber {
}
