package com.github.enid3.questionnaire.service.exception.questionnaire;

import com.github.enid3.questionnaire.service.exception.AbstractExceptionFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class QuestionnaireExceptionFactory extends AbstractExceptionFactory {

    public QuestionnaireExceptionFactory(MessageSource messageSource, Locale locale) {
        super(messageSource, locale);
    }

    public InvalidQuestionnaireException createNotFoundException(long id) {
        String message = messageSource.getMessage(
                "questionnaire.exception.not-found",
                new Long[] { id },
                locale);

        return new InvalidQuestionnaireException(message, InvalidQuestionnaireException.Reason.NOT_FOUND, id);
    }

    public InvalidQuestionnaireException createUserNotFoundException(long id) {
        String message = messageSource.getMessage(
                "questionnaire.exception.user-not-found",
                new Long[] { id },
                locale);

        return new InvalidQuestionnaireException(message, InvalidQuestionnaireException.Reason.USER_NOT_FOUND, id);
    }

    public InvalidQuestionnaireException createNoActiveFieldsException(long id) {
        String message = messageSource.getMessage(
                "questionnaire.exception.no-active-fields",
                new Long[] { id },
                locale);

        return new InvalidQuestionnaireException(message, InvalidQuestionnaireException.Reason.NO_ACTIVE_FIELDS, id);
    }
}
