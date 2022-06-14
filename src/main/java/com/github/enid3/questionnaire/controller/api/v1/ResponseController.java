package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/response")
@RequiredArgsConstructor
@Slf4j
public class ResponseController {
    private final ResponseService responseService;
    private final SimpMessageSendingOperations messageSendingOperations;

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteResponse( @PathVariable long id ) {
        responseService.deleteResponse(id);
    }

}
