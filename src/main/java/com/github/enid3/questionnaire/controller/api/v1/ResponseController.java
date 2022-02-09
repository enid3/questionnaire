package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.response.ResponseDTO;
import com.github.enid3.questionnaire.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/response")
@Slf4j
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;
    private final SimpMessageSendingOperations messageSendingOperations;

    @GetMapping
    public Page<ResponseDTO> getResponses(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {
        return responseService.getResponsesByOwner(userDetails.getUsername(), pageable);
    }

    @PostMapping("/{ownerId}")
    public ResponseDTO saveResponse(
            @PathVariable long ownerId,
            @RequestBody Map<Long, String> responseData
    ) {
        ResponseDTO responseDTO = ResponseDTO
                .builder()
                .responses(responseData)
                .build();

        ResponseDTO response = responseService.createResponse(ownerId, responseDTO);
        messageSendingOperations.convertAndSend("/topic/responses", response);
        return response;
    }

}
