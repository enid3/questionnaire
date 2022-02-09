package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("A")
@RequiredArgsConstructor
@Slf4j
public class AutorizationComponent {
    private final FieldsRepository fieldsRepository;
    public boolean mayUpdateField(UserDetails principal, long fieldId){
        log.info("id: {}, emai:{}", fieldId, principal.getUsername());
        boolean res = fieldsRepository.existsByIdAndOwnerEmail(fieldId, principal.getUsername());
        log.info("res: ", res);
        return res;
    }

}
