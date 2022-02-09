package com.github.enid3.questionnaire.service.exception.questionnaire;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class QuestionnaireExceptionFactory {
    private final MessageSource messageSource;

    public InvalidQuestionnaireException createUserNotFoundException(long id) {
        String message = messageSource.getMessage(
                "questionnaire.exception.user-not-found",
                new Long[] { id },
                Locale.ROOT);

        return new InvalidQuestionnaireException(message, InvalidQuestionnaireException.Reason.USER_NOT_FOUND, id);
    }

    public InvalidQuestionnaireException createNoActiveFieldsException(long id) {
        String message = messageSource.getMessage(
                "questionnaire.exception.no-active-fields",
                new Long[] { id },
                Locale.ROOT);

        return new InvalidQuestionnaireException(message, InvalidQuestionnaireException.Reason.NO_ACTIVE_FIELDS, id);
    }
}
