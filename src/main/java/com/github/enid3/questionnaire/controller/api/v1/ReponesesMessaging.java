package com.github.enid3.questionnaire.controller.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ReponesesMessaging {
    @MessageMapping("/test")
    void handle(){
        log.info("something happend");
    }
}
