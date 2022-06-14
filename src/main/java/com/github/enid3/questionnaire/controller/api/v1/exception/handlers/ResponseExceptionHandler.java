package com.github.enid3.questionnaire.controller.api.v1.exception.handlers;

import com.github.enid3.questionnaire.controller.api.v1.exception.utils.ExceptionUtils;
import com.github.enid3.questionnaire.data.dto.Error;
import com.github.enid3.questionnaire.service.exception.response.InvalidResponseException;
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
public class ResponseExceptionHandler {
    private final static int POSTFIX = 3;

    private final MessageSource messageSource;

    @ExceptionHandler(InvalidResponseException.class)
    public ResponseEntity<Error> handleException(InvalidResponseException ex, Locale locale) {
        HttpStatus status;
        String message;

        switch (ex.getReason()) {
            case NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                message = messageSource.getMessage(
                        "response.exception.not-found",
                        new Long[] { ex.getId() },
                        locale);
                break;

            case QUESTIONNAIRE_NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                message = messageSource.getMessage(
                        "response.exception.questionnaire-not-found",
                        new Long[] { ex.getId() },
                        locale);
                break;

            case REQUIRED_FIELD_NOT_PROVIDED:
                status = HttpStatus.BAD_REQUEST;
                message = messageSource.getMessage(
                        "response.exception.required-not-provided",
                        new Long[] { ex.getId() },
                        locale);
                break;

            case RESPONSE_TO_INVALID_FIELD:
                status = HttpStatus.BAD_REQUEST;
                message = messageSource.getMessage(
                        "response.exception.invalid-field-response",
                        new Long[] { ex.getId() },
                        locale);
                break;

            case EMPTY_RESPONSE:
                status = HttpStatus.BAD_REQUEST;
                message = messageSource.getMessage("response.exception.empty", null, locale);
                break;

            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = messageSource.getMessage("response.exception.common", null, locale);
        }
        return ExceptionUtils.handle(status, message, POSTFIX);
    }
}
