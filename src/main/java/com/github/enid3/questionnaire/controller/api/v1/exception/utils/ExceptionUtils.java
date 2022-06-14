package com.github.enid3.questionnaire.controller.api.v1.exception.utils;

import com.github.enid3.questionnaire.data.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ExceptionUtils {
    public static int getErrorCode(HttpStatus status, int postfix) {
        return 100 * status.value() + postfix;
    }

    public static ResponseEntity<Error> handle(HttpStatus status, String message, int postfix) {
        int errorCode = getErrorCode(status, postfix);
        Error error = new Error(errorCode, message);
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
