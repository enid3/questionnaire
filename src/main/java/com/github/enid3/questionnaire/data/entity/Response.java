package com.github.enid3.questionnaire.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name="questionnaire_id", nullable = false)
    private Questionnaire questionnaire;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "response_values",
            joinColumns = @JoinColumn(name="response_id")
    )
    @MapKeyJoinColumn(name = "field_id")
    @Column(name="value")
    @JoinColumn(name = "response_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Map<Field, String> responses = new HashMap<>();
}
