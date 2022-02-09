package com.github.enid3.questionnaire.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class IdentifiableDTO {
    protected Long id;
}
