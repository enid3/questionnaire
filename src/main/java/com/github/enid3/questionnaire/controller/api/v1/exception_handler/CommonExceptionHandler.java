package com.github.enid3.questionnaire.controller.api.v1.exception_handler;

import com.github.enid3.questionnaire.data.dto.Error;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
@RequiredArgsConstructor
public class CommonExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleException(ServiceException ex) {
        return new Error(0, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleException(ConstraintViolationException ex) {
        return new Error(0, ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Error handleException(BadCredentialsException ex) {
        return new Error(0, "Bad credentials");
    }
}
