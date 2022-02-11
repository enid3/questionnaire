package com.github.enid3.questionnaire.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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
    @Column(name="response_id")
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "response_values", joinColumns = @JoinColumn(name = "response_id") )
    @MapKeyJoinColumn(name = "field_id")
    @Column(name="response_value")
    @JoinColumn(name = "response_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    private Map<Field, String> responses = new HashMap<>();
}
