package com.github.enid3.questionnaire.data.entity;

import com.github.enid3.questionnaire.data.dto.questionnaire.validation.QuestionnaireConstraints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    @NotEmpty
    @Size(min=QuestionnaireConstraints.MIN_NAME_SIZE,
            max=QuestionnaireConstraints.MAX_NAME_SIZE)
    @Column(nullable = false)
    private String name = "Default questionnaire";

    @Column(nullable = true)
    @Size(min=QuestionnaireConstraints.MIN_DESCRIPTION_SIZE,
            max=QuestionnaireConstraints.MAX_DESCRIPTION_SIZE)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Builder.Default
    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Field> fields = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Response> responses = new ArrayList<>();

    public Questionnaire(User owner) {
        this.owner = owner;
    }

    /*
    @Transient
    public boolean isReady() {
        return fields.size() >= 1;
    }

     */
}
