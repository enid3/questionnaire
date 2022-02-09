package com.github.enid3.questionnaire.service.exception.field;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class FieldExceptionFactory {
    private final MessageSource messageSource;

    public InvalidFieldException createNotFoundException(long id) {
        String message = messageSource.getMessage(
                "field.exception.not-found",
                new Long[] { id },
                Locale.ROOT);

        return new InvalidFieldException(message, InvalidFieldException.Reason.NOT_FOUND, id);
    }
}
