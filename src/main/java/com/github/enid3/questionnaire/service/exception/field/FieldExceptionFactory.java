package com.github.enid3.questionnaire.service.exception.field;

import com.github.enid3.questionnaire.service.exception.AbstractExceptionFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class FieldExceptionFactory extends AbstractExceptionFactory {

    public FieldExceptionFactory(MessageSource messageSource, Locale locale) {
        super(messageSource, locale);
    }

    public InvalidFieldException createNotFoundException(long id) {
        String message = messageSource.getMessage(
                "field.exception.questionnaire-not-found",
                new Long[] { id },
                locale);

        return new InvalidFieldException(message, InvalidFieldException.Reason.NOT_FOUND, id);
    }
    public InvalidFieldException createQuestionnaireNotFoundException(long id) {
        String message = messageSource.getMessage(
                "field.exception.questionnaire-not-found",
                new Long[] { id },
                locale);

        return new InvalidFieldException(message, InvalidFieldException.Reason.QUESTIONNAIRE_NOT_FOUND, id);
    }
}
