package com.github.enid3.questionnaire.data.dto;

import lombok.Value;

@Value
public class Error {
    int code;
    String message;
}
