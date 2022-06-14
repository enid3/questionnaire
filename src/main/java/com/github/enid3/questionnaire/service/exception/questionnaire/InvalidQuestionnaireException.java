package com.github.enid3.questionnaire.service.exception.questionnaire;

import com.github.enid3.questionnaire.service.exception.ServiceException;
import lombok.Getter;

public class InvalidQuestionnaireException extends ServiceException {
    @Getter
    private Reason reason;
    @Getter
    private Long id;

    public InvalidQuestionnaireException(String message, Reason reason, Long id) {
        super(message);
        this.reason = reason;
        this.id = id;
    }

    public enum Reason {
        USER_NOT_FOUND, NO_ACTIVE_FIELDS, NOT_FOUND
    }
}
