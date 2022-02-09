package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.entity.Field;

public interface OwnershipService {
    void setOwner(String email, Field field);
}
