package com.github.enid3.questionnaire.service.exception.field;

import com.github.enid3.questionnaire.service.exception.ServiceException;
import lombok.Getter;

public class InvalidFieldException extends ServiceException {
    @Getter
    private Reason reason;
    @Getter
    private Long id;

    public InvalidFieldException(String message, Reason reason, Long id) {
        super(message);
        this.reason = reason;
        this.id = id;
    }

    public enum Reason {
        NOT_FOUND, QUESTIONNAIRE_NOT_FOUND
    }
}
