package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.entity.Questionnaire;

public interface OwnershipService {
    void setOwner(String email, Questionnaire questionnaire);
}
