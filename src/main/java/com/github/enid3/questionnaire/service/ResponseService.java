package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.dto.response.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ResponseService {
    ResponseDTO createResponse(long questionnaireId, ResponseDTO responseDTO);

    Page<ResponseDTO> getResponsesByQuestionnaire(long questionnaireId, Pageable pageable);

    boolean deleteResponseByFieldId(long id);
    void deleteResponse(long id);
}
