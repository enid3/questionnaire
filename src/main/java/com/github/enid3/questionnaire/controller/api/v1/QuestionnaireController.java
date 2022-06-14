package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldLabelDTO;
import com.github.enid3.questionnaire.data.dto.questionnaire.*;
import com.github.enid3.questionnaire.data.dto.response.ResponseDTO;
import com.github.enid3.questionnaire.service.FieldService;
import com.github.enid3.questionnaire.service.QuestionnaireService;
import com.github.enid3.questionnaire.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;
    private final ResponseService responseService;
    private final FieldService fieldService;

    @PostMapping
    public QuestionnaireInfoLightDTO createQuestionnaire(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody QuestionnaireCreateDTO questionnaireCreateDTO
    ) {
        return questionnaireService.createQuestionnaire(userDetails.getUsername(), questionnaireCreateDTO);
    }

    @GetMapping("/own")
    public List<QuestionnaireInfoLightDTO> getAllOwnQuestionnaires(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return questionnaireService.getAllQuestionnaireInfoByOwner(userDetails.getUsername());
    }

    @GetMapping
    public Page<QuestionnaireInfoDTO> getAllReadyQuestionnaires(
            Pageable pageable
    ) {
        return questionnaireService.getAllReadyQuestionnaires(pageable);
    }

    @PutMapping("/{questionnaireId}")
    public QuestionnaireInfoLightDTO updateQuestionnaire(
            @PathVariable long questionnaireId,
            @RequestBody QuestionnaireUpdateInfoDTO questionnaireUpdateInfoDTO
    ) {
        return questionnaireService.updateQuestionnaireInfo(questionnaireId, questionnaireUpdateInfoDTO);
    }

    @GetMapping("/{questionnaireId}")
    public QuestionnaireDTO getQuestionnaire(
            @PathVariable long questionnaireId
    ) {
        return questionnaireService.getQuestionnaire(questionnaireId);
    }
    @GetMapping("/{questionnaireId}/info")
    public QuestionnaireDTO getQuestionnaireInfo(
            @PathVariable long questionnaireId
    ) {
        return questionnaireService.getQuestionnaire(questionnaireId);
    }

    @GetMapping("/{questionnaireId}/featured")
    public Page<QuestionnaireInfoDTO> getQuestionnaireInfo(
            @PathVariable long questionnaireId,
            Pageable pageable
    ) {
        return questionnaireService.getFeaturedQuestionnaires(questionnaireId, pageable);
    }

    @GetMapping("/{questionnaireId}/field")
    public Page<FieldDTO> getAllFields(
            @PathVariable long questionnaireId,
            Pageable pageable
    ) {
        return fieldService.getAllFieldsByQuestionnaire(questionnaireId, pageable);
    }

    @GetMapping("/{questionnaireId}/field/active")
    public List<FieldDTO> getAllActiveFields(
            @PathVariable long questionnaireId
    ) {
        return fieldService.getAllActiveFieldsByQuestionnaire(questionnaireId);
    }

    @GetMapping("{questionnaireId}/field/active/label")
    public List<FieldLabelDTO> getAllActiveFieldLabels(
            @PathVariable long questionnaireId
            ) {
        return fieldService.getAllActiveFieldLabelsByQuestionnaire(questionnaireId);
    }


    @PostMapping("/{questionnaireId}/field")
    public FieldDTO createField(
            @PathVariable long questionnaireId,
            @RequestBody FieldDTO fieldDTO
    ) {
        return fieldService.createField(questionnaireId, fieldDTO);
    }

    @DeleteMapping("/{questionnaireId}")
    @Transactional
    public void deleteQuestionnaire(
            @PathVariable long questionnaireId
    ) {
        questionnaireService.deleteQuestionnaire(questionnaireId);
    }

    @GetMapping("{questionnaireId}/response")
    public Page<ResponseDTO> getResponses(
            @PathVariable long questionnaireId,
            Pageable pageable
    ) {
        return responseService.getResponsesByQuestionnaire(questionnaireId, pageable);
    }

    @PostMapping("{questionnaireId}/response")
    public ResponseDTO createResponse(
            @PathVariable long questionnaireId,
            @RequestBody Map<Long, String> responseData
    ) {
        ResponseDTO responseDTO = ResponseDTO
                .builder()
                .responses(responseData)
                .build();

        ResponseDTO response = responseService.createResponse(questionnaireId, responseDTO);
        //messageSendingOperations.convertAndSend("/topic/responses", response);
        return response;
    }
}
