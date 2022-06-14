package com.github.enid3.questionnaire.service.exception.response;

import com.github.enid3.questionnaire.service.exception.ServiceException;
import lombok.Getter;

public class InvalidResponseException extends ServiceException {
    @Getter
    private Reason reason;
    @Getter
    private Long id;

    public InvalidResponseException(String message, Reason reason, Long id) {
        super(message);
        this.reason = reason;
        this.id = id;
    }

    public enum Reason {
        NOT_FOUND,
        QUESTIONNAIRE_NOT_FOUND,
        REQUIRED_FIELD_NOT_PROVIDED,
        RESPONSE_TO_INVALID_FIELD,
        EMPTY_RESPONSE
    }
}
