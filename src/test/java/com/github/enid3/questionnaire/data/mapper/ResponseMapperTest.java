package com.github.enid3.questionnaire.data.mapper;

import com.github.enid3.questionnaire.data.dto.response.ResponseDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Questionnaire;
import com.github.enid3.questionnaire.data.entity.Response;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseMapperTest {
    private static final ResponseMapper responseMapper = Mappers.getMapper(ResponseMapper.class);

    private static final long RESPONSE_ID = 921L;

    private static final long FIELD_ID_1 = 10L;
    private static final Field field1 = Field.builder().id(FIELD_ID_1).build();
    private static final String RESPONSE_1 = "Some useful response";

    private static final long FIELD_ID_2 = 20L;
    private static final Field field2 = Field.builder().id(FIELD_ID_2).build();
    private static final String RESPONSE_2 = "Another response";

    private static final long FIELD_ID_3 = 33L;
    private static final Field field3 = Field.builder().id(FIELD_ID_3).build();
    private static final String RESPONSE_3 = "Third extremely useful response response";

    private static final Questionnaire questionnaire = Questionnaire.builder().id(1L).build();

    private static final Response responseToConvect = Response.builder()
            .id(RESPONSE_ID)
            .questionnaire(questionnaire)
            .responses(Map.of(
                    field1, RESPONSE_1,
                    field2, RESPONSE_2,
                    field3, RESPONSE_3
            ))
            .build();

    private static final ResponseDTO expectedResponseDTO = ResponseDTO.builder()
            .id(RESPONSE_ID)
            .responses(Map.of(
                    FIELD_ID_1, RESPONSE_1,
                    FIELD_ID_2, RESPONSE_2,
                    FIELD_ID_3, RESPONSE_3
            ))
            .build();

    @Test
    public void toResponseDTOShouldReturnCorrectDTO() {
        ResponseDTO dto = responseMapper.toResponseDTO(responseToConvect);

        assertEquals(expectedResponseDTO.getId(), dto.getId());
        assertEquals(expectedResponseDTO.getResponses(), dto.getResponses());
    }
}