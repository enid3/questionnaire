package com.github.enid3.questionnaire.data.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "response_values")
    @Column(name="value")
    @MapKeyJoinColumn(name = "field_id")
    private Map<Field, String> responses = new HashMap<>();
}
