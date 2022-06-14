package com.github.enid3.questionnaire.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.enid3.questionnaire.data.dto.field.validation.FieldConstraints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaire;

    @NotBlank
    @NotNull
    @Size(min=FieldConstraints.MIN_LABEL_SIZE,
            max=FieldConstraints.MAX_LABEL_SIZE)
    @Column(nullable = false)
    private String label;

    @NotNull
    @Column(nullable = false)
    private Type type;

    @Builder.Default
    @NotNull
    @Column(nullable = false)
    private Boolean isRequired = true;

    @Builder.Default
    @NotNull
    @Column(nullable = false)
    private Boolean isActive = true;


    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @JoinColumn(name = "options")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<String> options = new HashSet<>();

    public enum Type {
        SINGLE_LINE_TEXT("Single line text"),
        MULTILINE_TEXT("Multiline text"),
        RADIO_BUTTON("Radio button"),
        CHECKBOX("Checkbox"),
        COMBOBOX("Combobox"),
        DATE("Date");

        final private String description;

        Type (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
