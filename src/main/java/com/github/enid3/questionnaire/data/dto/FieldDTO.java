package com.github.enid3.questionnaire.data.dto;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.FieldType;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class FieldDTO {
    private Long id;

    private String label;
    private FieldType type;

    public Boolean isRequired;
    public Boolean isActive;

    private String options;

    public FieldDTO() {}
    public FieldDTO(Field field) {
        this.id = field.getId();
        this.label = field.getLabel();
        this.type = field.getType();
        this.isRequired = field.getIsRequired();
        this.isActive = field.getIsActive();
        this.options = field.getOptions()
                .stream()
                .collect(Collectors.joining("\n"));
    }


}
