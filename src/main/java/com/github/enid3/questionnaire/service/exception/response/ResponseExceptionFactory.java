package com.github.enid3.questionnaire.service.exception.response;

import com.github.enid3.questionnaire.service.exception.AbstractExceptionFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ResponseExceptionFactory extends AbstractExceptionFactory {

    public ResponseExceptionFactory(MessageSource messageSource, Locale locale) {
        super(messageSource, locale);
    }

    public InvalidResponseException createNotFoundException(long id) {
        String message = messageSource.getMessage(
                "response.exception.not-found",
                new Long[] { id },
                locale);

        return new InvalidResponseException(message, InvalidResponseException.Reason.NOT_FOUND, id);
    }

    public InvalidResponseException createQuestionnaireNotFoundException(long id) {
        String message = messageSource.getMessage(
                "response.exception.questionnaire-not-found",
                new Long[] { id },
                locale);

        return new InvalidResponseException(message, InvalidResponseException.Reason.QUESTIONNAIRE_NOT_FOUND, id);
    }

    public InvalidResponseException createRequiredFieldNotProvidedException(long fieldId) {
        String message = messageSource.getMessage(
                "response.exception.required-not-provided",
                new Long[] { fieldId },
                locale);

        return new InvalidResponseException(message, InvalidResponseException.Reason.REQUIRED_FIELD_NOT_PROVIDED, fieldId);
    }

    public InvalidResponseException createResponseToInvalidFieldException(long fieldId) {
        String message = messageSource.getMessage(
                "response.exception.invalid-field-response",
                new Long[] { fieldId },
                locale);

        return new InvalidResponseException(message, InvalidResponseException.Reason.RESPONSE_TO_INVALID_FIELD, fieldId);
    }

    public InvalidResponseException createEmptyResponseException() {
        String message = messageSource.getMessage(
                "response.exception.empty",
                null,
                locale);

        return new InvalidResponseException(message, InvalidResponseException.Reason.EMPTY_RESPONSE, null);
    }
}
