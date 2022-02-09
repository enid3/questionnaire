package com.github.enid3.questionnaire.data.dto.response;

import com.github.enid3.questionnaire.data.dto.IdentifiableDTO;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
@SuperBuilder
public class ResponseDTO extends IdentifiableDTO {
    @NotNull(message = "{response.validation-message.responses-not-empty}")
    private Map<Long, String> responses = new HashMap<>();
}
