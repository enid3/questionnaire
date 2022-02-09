package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.dto.questionnaire.QuestionnaireDTO;
import org.springframework.validation.annotation.Validated;

@Validated
public interface QuestionnaireService {
    QuestionnaireDTO getQuestionnaireByUser(long userId);
}
