package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.dto.response.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ResponseService {
    ResponseDTO createResponse(long ownerId, ResponseDTO responseDTO);

    Page<ResponseDTO> getResponsesByOwner(String ownerEmail, Pageable pageable);

}
