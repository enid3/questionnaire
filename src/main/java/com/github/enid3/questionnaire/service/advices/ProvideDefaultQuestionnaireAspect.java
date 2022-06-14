package com.github.enid3.questionnaire.service.advices;

import com.github.enid3.questionnaire.data.dto.questionnaire.QuestionnaireCreateDTO;
import com.github.enid3.questionnaire.data.dto.user.UserResponseDTO;
import com.github.enid3.questionnaire.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Slf4j
@Component
public class ProvideDefaultQuestionnaireAspect {
    private final QuestionnaireService questionnaireService;

    @AfterReturning(value = "execution(* com.github.enid3.questionnaire.service.UserService.createUser(..))", returning = "dto")
    public UserResponseDTO createDefaultQuestionnaire(UserResponseDTO dto) {
        QuestionnaireCreateDTO questionnaireCreateDTO = QuestionnaireCreateDTO.builder()
                .name("Default questionnaire")
                .description(null)
                .build();
        questionnaireService.createQuestionnaire(dto.getEmail(), questionnaireCreateDTO);
        return dto;
    }


}
