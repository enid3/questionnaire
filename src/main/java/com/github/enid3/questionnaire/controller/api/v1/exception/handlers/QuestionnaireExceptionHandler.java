package com.github.enid3.questionnaire.controller.api.v1.exception.handlers;

import com.github.enid3.questionnaire.controller.api.v1.exception.utils.ExceptionUtils;
import com.github.enid3.questionnaire.data.dto.Error;
import com.github.enid3.questionnaire.service.exception.questionnaire.InvalidQuestionnaireException;
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
public class QuestionnaireExceptionHandler {
    private final static int POSTFIX = 2;

    private final MessageSource messageSource;

    @ExceptionHandler(InvalidQuestionnaireException.class)
    public ResponseEntity<Error> handleException(InvalidQuestionnaireException ex, Locale locale) {
        HttpStatus status;
        String message;

        switch (ex.getReason()) {
            case USER_NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                message = messageSource.getMessage(
                        "questionnaire.exception.user-not-found",
                        new Long[] { ex.getId() },
                        locale);
                break;

            case NO_ACTIVE_FIELDS:
                status = HttpStatus.NOT_FOUND;
                message = messageSource.getMessage(
                        "questionnaire.exception.no-active-fields",
                        new Long[] { ex.getId() },
                        locale);
                break;

            case NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                message = messageSource.getMessage(
                        "questionnaire.exception.not-found",
                        new Long[] { ex.getId() },
                        locale);
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = messageSource.getMessage("questionnaire.exception.common", null, locale);
        }
        return ExceptionUtils.handle(status, message, POSTFIX);
    }
}
