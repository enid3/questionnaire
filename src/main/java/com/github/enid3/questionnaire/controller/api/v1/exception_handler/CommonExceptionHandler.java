package com.github.enid3.questionnaire.controller.api.v1.exception_handler;

import com.github.enid3.questionnaire.data.dto.Error;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;


//@RestControllerAdvice
//@RequiredArgsConstructor
public class CommonExceptionHandler {

    //@ExceptionHandler(ServiceException.class)
    public Error handleException(ServiceException ex) {
        return new Error(0, ex.getMessage());
    }

    //@ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleException(ConstraintViolationException ex) {
        return new Error(0, ex.getMessage());
    }
}
