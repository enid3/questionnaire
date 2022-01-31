package com.github.enid3.questionnaire.data.dto;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Response;
import lombok.Data;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ResponseDTO {
    @Id
    private Long id;
    private Map<Long, String> responses = new HashMap<>();

    public ResponseDTO(Response response){
        this.id = response.getId();
        this.responses = response
                .getResponses()
                .keySet()
                .stream()
                .collect(Collectors.toMap(
                        Field::getId,
                        f -> response.getResponses().get(f)
                ));
    }
}
