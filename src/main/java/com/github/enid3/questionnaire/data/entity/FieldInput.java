package com.github.enid3.questionnaire.data.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class FieldInput {
    private Long fieldId;
    private String fieldResponse;
}
