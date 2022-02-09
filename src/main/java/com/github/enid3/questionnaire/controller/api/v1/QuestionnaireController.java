package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.questionnaire.QuestionnaireDTO;
import com.github.enid3.questionnaire.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    @GetMapping("/{ownerId}")
    public QuestionnaireDTO getQuestionnaire(
            @PathVariable long ownerId
    ) {
        return questionnaireService.getQuestionnaireByUser(ownerId);
    }
}
