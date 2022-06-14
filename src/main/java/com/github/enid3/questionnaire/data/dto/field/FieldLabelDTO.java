package com.github.enid3.questionnaire.data.dto.field;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldLabelDTO extends IdentifiableDTO {
    private String label;
}
