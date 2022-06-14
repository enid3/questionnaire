package com.github.enid3.questionnaire.service.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

@RequiredArgsConstructor
public abstract class AbstractExceptionFactory {
    protected final MessageSource messageSource;
    protected final Locale locale;
}
