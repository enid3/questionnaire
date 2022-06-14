package com.github.enid3.questionnaire.service.exception.user.auth;

import com.github.enid3.questionnaire.service.exception.ServiceException;
import lombok.Getter;

public class InvalidAuthException extends ServiceException {
    @Getter
    private Reason reason;
    @Getter
    private String email;


    public InvalidAuthException(String message, Reason reason, String email) {
        super(message);
        this.reason = reason;
        this.email = email;
    }

    public enum Reason {
        USER_NOT_FOUND,
    }
}
