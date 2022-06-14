package com.github.enid3.questionnaire.controller.api.v1.exception.handlers;

import com.github.enid3.questionnaire.controller.api.v1.exception.utils.ExceptionUtils;
import com.github.enid3.questionnaire.data.dto.Error;
import com.github.enid3.questionnaire.service.exception.user.InvalidUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class UserExceptionHandler {
    private final static int POSTFIX = 4;

    private final MessageSource messageSource;

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Error> handleException(InvalidUserException ex, Locale locale) {
        HttpStatus status;
        String message;

        switch (ex.getReason()) {
            case USER_NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                message = messageSource.getMessage(
                        "user.exception.user-not-found",
                        new String[] { ex.getEmail() },
                        locale);
                break;

            case USER_EMAIL_ALREADY_IN_USE:
                status = HttpStatus.CONFLICT;
                message = messageSource.getMessage("user.exception.email-already-in-us", null, locale);
                break;

            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = messageSource.getMessage("user.exception.common", null, locale);
        }
        return ExceptionUtils.handle(status, message, POSTFIX);
    }
}
