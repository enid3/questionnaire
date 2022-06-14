package com.github.enid3.questionnaire.service.exception.user.auth;

import com.github.enid3.questionnaire.service.exception.AbstractExceptionFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AuthExceptionFactory extends AbstractExceptionFactory {

    public AuthExceptionFactory(MessageSource messageSource, Locale locale) {
        super(messageSource, locale);
    }

    public BadCredentialsException createInvalidCredentialsException() {
        String message = messageSource.getMessage(
                "auth.exception.bad-credentials",
                null,
                locale);
        return new BadCredentialsException(message);
    }

}
