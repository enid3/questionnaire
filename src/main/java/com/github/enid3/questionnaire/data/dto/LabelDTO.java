package com.github.enid3.questionnaire.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@SqlResultSetMapping(
        name="LabelDTOMapping",
        classes = {
                @ConstructorResult(targetClass = LabelDTO.class,
                        columns = {
                                @ColumnResult(name="id", type = Long.class),
                                @ColumnResult(name="label", type = String.class)
                        }
                ) }
)
@NamedNativeQuery(
        name="select_all_labels",
        query = "select id, label FROM field where owner_id=?",
        resultSetMapping = "LabelDTOMapping"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelDTO {
    @Id
    private Long id;
    private String label;
}
