package com.github.enid3.questionnaire.service.exception.user;

import com.github.enid3.questionnaire.service.exception.AbstractExceptionFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UserExceptionFactory extends AbstractExceptionFactory {

    public UserExceptionFactory(MessageSource messageSource, Locale locale) {
        super(messageSource, locale);
    }

    public InvalidUserException createUserNotFoundException(String email) {
        String message = messageSource.getMessage(
                "user.exception.user-not-found",
                new String[] { email },
                locale);

        return new InvalidUserException(message, InvalidUserException.Reason.USER_NOT_FOUND, email);
    }

    public InvalidUserException createUserEmailAlreadyInUsException(String email) {
        String message = messageSource.getMessage(
                "user.exception.email-already-in-use",
                new String[] { email },
                locale);

        return new InvalidUserException(message, InvalidUserException.Reason.USER_EMAIL_ALREADY_IN_USE, email);
    }

}
