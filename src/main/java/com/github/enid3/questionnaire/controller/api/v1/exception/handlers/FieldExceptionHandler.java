package com.github.enid3.questionnaire.controller.api.v1.exception.handlers;

import com.github.enid3.questionnaire.controller.api.v1.exception.utils.ExceptionUtils;
import com.github.enid3.questionnaire.data.dto.Error;
import com.github.enid3.questionnaire.service.exception.field.InvalidFieldException;
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
public class FieldExceptionHandler {
    private final static int POSTFIX = 1;

    private final MessageSource messageSource;

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<Error> handleException(InvalidFieldException ex, Locale locale) {
        HttpStatus status;
        String message;
        switch (ex.getReason()) {
            case NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                message = messageSource.getMessage(
                            "field.exception.not-found",
                            new Long[] { ex.getId() },
                            locale);
                break;
            case QUESTIONNAIRE_NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                message = messageSource.getMessage(
                        "field.exception.questionnaire-not-found",
                        new Long[] { ex.getId() },
                        locale);
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = messageSource.getMessage("field.exception.common", null, locale);
        }
        return ExceptionUtils.handle(status, message, POSTFIX);
    }
}
