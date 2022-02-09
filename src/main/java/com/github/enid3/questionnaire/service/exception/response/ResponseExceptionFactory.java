package com.github.enid3.questionnaire.service.exception.response;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ResponseExceptionFactory {
    private final MessageSource messageSource;

    public InvalidResponseException createUserNotFoundException(long id) {
        String message = messageSource.getMessage(
                "response.exception.user-not-found",
                new Long[] { id },
                Locale.ROOT);

        return new InvalidResponseException(message, InvalidResponseException.Reason.USER_NOT_FOUND, id);
    }

    public InvalidResponseException createNoSuchFieldException(long fieldId) {
        String message = messageSource.getMessage(
                "response.exception.required-not-provided",
                new Long[] { fieldId },
                Locale.ROOT);

        return new InvalidResponseException(message, InvalidResponseException.Reason.REQUIRED_FIELD_NOT_PROVIDED, fieldId);
    }

    public InvalidResponseException createResponseToInvalidFieldException(long fieldId) {
        String message = messageSource.getMessage(
                "response.exception.invalid-field-response",
                new Long[] { fieldId },
                Locale.ROOT);

        return new InvalidResponseException(message, InvalidResponseException.Reason.RESPONSE_TO_INVALID_FIELD, fieldId);
    }

    public InvalidResponseException createEmptyResponseException() {
        String message = messageSource.getMessage(
                "response.exception.empty",
                null,
                Locale.ROOT);

        return new InvalidResponseException(message, InvalidResponseException.Reason.EMPTY_RESPONSE, null);
    }
}
