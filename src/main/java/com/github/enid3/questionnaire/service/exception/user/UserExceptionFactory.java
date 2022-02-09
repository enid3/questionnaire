package com.github.enid3.questionnaire.service.exception.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UserExceptionFactory {
    private final MessageSource messageSource;

    public InvalidUserException createUserNotFoundException(String email) {
        String message = messageSource.getMessage(
                "user.exception.user-not-found",
                new String[] { email },
                Locale.ROOT);

        return new InvalidUserException(message, InvalidUserException.Reason.USER_NOT_FOUND, 0l);
    }

}
