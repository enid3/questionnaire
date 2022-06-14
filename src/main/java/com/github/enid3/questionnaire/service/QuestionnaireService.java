package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.dto.questionnaire.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface QuestionnaireService {
    QuestionnaireInfoLightDTO createQuestionnaire(String ownerEmail, @Valid QuestionnaireCreateDTO questionnaireCreateDTO);

    QuestionnaireDTO getQuestionnaire(long id);
    QuestionnaireInfoDTO getQuestionnaireInfo(long id);


    List<QuestionnaireInfoDTO> getAllQuestionnaireInfoByOwner(long ownerId);

    Page<QuestionnaireInfoDTO> getAllReadyQuestionnaires(Pageable pageable);
    Page<QuestionnaireInfoDTO> getFeaturedQuestionnaires(long questionnaireId, Pageable pageable);
    //Page<QuestionnaireInfoDTO> getAllReadyQuestionnaires(Pageable pageable);

    List<QuestionnaireInfoLightDTO> getAllQuestionnaireInfoByOwner(String ownerEmail);
    Page<QuestionnaireInfoDTO> getAllQuestionnaireInfo(Pageable pageable);

    QuestionnaireInfoLightDTO updateQuestionnaireInfo(long id, @Validated QuestionnaireUpdateInfoDTO questionnaireUpdateInfoDTO);

    void deleteQuestionnaire(long id);

    boolean questionnaireExists(long id);
}
