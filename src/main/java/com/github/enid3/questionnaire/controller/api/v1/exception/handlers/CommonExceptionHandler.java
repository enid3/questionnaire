package com.github.enid3.questionnaire.controller.api.v1.exception.handlers;

import com.github.enid3.questionnaire.controller.api.v1.exception.utils.ExceptionUtils;
import com.github.enid3.questionnaire.data.dto.Error;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.stream.Collectors;


@RestControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE)
public class CommonExceptionHandler {
    private final static int POSTFIX = 0;
    private final MessageSource messageSource;

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Error> handleException(ServiceException ex, Locale locale) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = messageSource.getMessage("service.exception.common", null, locale);
        return ExceptionUtils.handle(status, message, POSTFIX);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleException(ConstraintViolationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage();
        return ExceptionUtils.handle(status, message, POSTFIX);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleException(MethodArgumentNotValidException ex, Locale locale) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getAllErrors()
                .stream()
                .map((e) -> messageSource.getMessage(e, locale))
                .collect(Collectors.joining("\n"));

        return ExceptionUtils.handle(status, message, POSTFIX);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> handleException(BadCredentialsException ex, Locale locale) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        String message = messageSource.getMessage("auth.exception.bad-accreditation", null, locale);
        return ExceptionUtils.handle(status, message, POSTFIX);
    }
}
