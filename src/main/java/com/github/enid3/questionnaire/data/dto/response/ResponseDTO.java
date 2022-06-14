package com.github.enid3.questionnaire.data.dto.response;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO extends IdentifiableDTO {
    @Builder.Default
    @NotNull(message = "{response.validation-message.responses-not-empty}")
    private Map<Long, String> responses = new HashMap<>();
}
