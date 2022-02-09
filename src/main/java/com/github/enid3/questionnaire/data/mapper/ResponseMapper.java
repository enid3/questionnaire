package com.github.enid3.questionnaire.data.mapper;

import com.github.enid3.questionnaire.data.dto.response.ResponseDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Response;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ResponseMapper {
    public ResponseDTO toResponseDTO(Response response) {
        return ResponseDTO
                .builder()
                .id(response.getId())
                .responses(response
                        .getResponses()
                        .keySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Field::getId,
                                f -> response.getResponses().get(f)
                        )))
                .build();

    }
}
