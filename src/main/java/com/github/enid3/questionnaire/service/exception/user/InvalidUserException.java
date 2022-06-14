package com.github.enid3.questionnaire.service.exception.user;

import com.github.enid3.questionnaire.service.exception.ServiceException;
import lombok.Getter;

public class InvalidUserException extends ServiceException {
    @Getter
    private Reason reason;
    @Getter
    private String email;


    public InvalidUserException(String message, Reason reason, String email) {
        super(message);
        this.reason = reason;
        this.email = email;
    }

    public enum Reason {
        USER_NOT_FOUND, USER_EMAIL_ALREADY_IN_USE
    }
}
