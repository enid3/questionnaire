package com.github.enid3.questionnaire.data.entity;

import com.github.enid3.questionnaire.data.dto.FieldDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name="fields")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String label;
    private FieldType type;

    private Boolean isRequired;
    private Boolean isActive;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private Set<String> options = new HashSet<>();

    public Field() {}
    public Field(FieldDTO dto) {
        this.id = dto.getId();
        this.label = dto.getLabel();
        this.type = dto.getType();
        this.isRequired = dto.getIsRequired();
        this.isActive = dto.getIsActive();
        this.options = new HashSet<>(Arrays
                        .stream(dto.getOptions().split("\n"))
                        .collect(Collectors.toSet()));
    }
}
