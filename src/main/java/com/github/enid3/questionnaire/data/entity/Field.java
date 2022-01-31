package com.github.enid3.questionnaire.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @NotBlank
    @Size(max=100)
    private String label;

    @NotNull
    private Type type;

    @NotNull
    private Boolean isRequired = true;
    @NotNull
    private Boolean isActive = true;


    @NotNull
    @ElementCollection(fetch = FetchType.LAZY)
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
