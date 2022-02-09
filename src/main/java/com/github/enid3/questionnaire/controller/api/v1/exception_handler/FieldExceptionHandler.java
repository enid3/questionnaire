package com.github.enid3.questionnaire.controller.api.v1.exception_handler;

import com.github.enid3.questionnaire.data.dto.Error;
import com.github.enid3.questionnaire.service.exception.field.InvalidFieldException;

//@RestControllerAdvice
//@RequiredArgsConstructor
public class FieldExceptionHandler {

    //@ExceptionHandler(InvalidFieldException.class)
    public Error handleException(InvalidFieldException ex) {
        return new Error(0, ex.getMessage());
    }
}
