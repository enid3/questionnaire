package com.github.enid3.questionnaire.service.exception.user;

import com.github.enid3.questionnaire.service.exception.ServiceException;
import lombok.Getter;

public class InvalidUserException extends ServiceException {
    @Getter
    private Reason reason;
    @Getter
    private Long id;

    public InvalidUserException(String message, Reason reason, Long id) {
        super(message);
        this.reason = reason;
        this.id = id;
    }

    public enum Reason {
        USER_NOT_FOUND,
    }
}
