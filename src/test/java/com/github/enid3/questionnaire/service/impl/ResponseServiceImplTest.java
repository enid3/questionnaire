package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.response.ResponseDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Response;
import com.github.enid3.questionnaire.data.mapper.ResponseMapper;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.ResponseRepository;
import com.github.enid3.questionnaire.service.QuestionnaireService;
import com.github.enid3.questionnaire.service.exception.response.InvalidResponseException;
import com.github.enid3.questionnaire.service.exception.response.ResponseExceptionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResponseServiceImplTest {
    @Mock
    private ResponseRepository responseRepository;
    @Mock
    private FieldsRepository fieldsRepository;
    @Mock
    private QuestionnaireService questionnaireService;

    private static final ResponseExceptionFactory responseExceptionFactory =
            new ResponseExceptionFactory(Mockito.mock(MessageSource.class), Locale.ENGLISH);

    private static final ResponseMapper responseMapper = Mappers.getMapper(ResponseMapper.class);

    private ResponseServiceImpl responseService;
    @BeforeEach
    void init() {
        responseService = new ResponseServiceImpl(
                responseRepository,
                fieldsRepository,
                responseMapper,
                responseExceptionFactory,
                questionnaireService
        );
    }


    private static final long NON_EXISTING_RESPONSE_ID = 33L;
    private static final long QUESTIONNAIRE_ID = 30L;
    private static final long NON_EXISTING_QUESTIONNAIRE_ID = 33L;

    private static final Field fieldRA = Field.builder()
            .id(1L)
            .label("single line r a")
            .type(Field.Type.SINGLE_LINE_TEXT)
            .isRequired(true)
            .isActive(true)
            .build();
    private static final Field fieldA = Field.builder()
            .id(2L)
            .label("single line a")
            .type(Field.Type.SINGLE_LINE_TEXT)
            .isRequired(false)
            .isActive(true)
            .build();
    private static final Field fieldR = Field.builder()
            .id(3L)
            .label("single line r")
            .type(Field.Type.SINGLE_LINE_TEXT)
            .isRequired(true)
            .isActive(false)
            .build();
    private static final Field field = Field.builder()
            .id(3L)
            .label("single line")
            .type(Field.Type.SINGLE_LINE_TEXT)
            .isRequired(false)
            .isActive(false)
            .build();

    //private static final List<Field> fields = List.of(field, fieldA, fieldR, fieldRA);
    private static final List<Field> activeFields = List.of(fieldA, fieldRA);

    private static final ResponseDTO emptyResponseDto = ResponseDTO.builder().build();

    private static final ResponseDTO minCorrectResponseDto = ResponseDTO.builder()
            .id(51L)
            .responses(Map.of(fieldRA.getId(), "123"))
            .build();

    private static final ResponseDTO maxCorrectResponseDto = ResponseDTO.builder()
            .id(52L)
            .responses(Map.of(
                    fieldRA.getId(), "123",
                    fieldA.getId(), "456"
                    ))
            .build();

    private static final long NON_EXISTING_FIELD_ID = 111L;
    private static final ResponseDTO toNonExistingFieldResponseDto = ResponseDTO.builder()
            .id(53L)
            .responses(Map.of(
                fieldRA.getId(), "123",
                NON_EXISTING_FIELD_ID, "456" ))
            .build();
    private static final long FROM_OTHER_QUESTIONNAIRE_FIELD_ID = 112L;
    private static final ResponseDTO toFieldFromOtherQuestionnaireResponseDto = ResponseDTO.builder()
            .id(53L)
            .responses(Map.of(
                    fieldRA.getId(), "123",
                    FROM_OTHER_QUESTIONNAIRE_FIELD_ID, "456" ))
            .build();
    private static final ResponseDTO toNotActiveFieldResponseDto = ResponseDTO.builder()
            .id(53L)
            .responses(Map.of(
                    fieldRA.getId(), "123",
                    field.getId(), "456"
            ))
            .build();
    private static final ResponseDTO toNotActiveRequiredFieldResponseDto = ResponseDTO.builder()
            .id(53L)
            .responses(Map.of(
                    fieldRA.getId(), "123",
                    fieldR.getId(), "456"
            ))
            .build();

    private static final ResponseDTO requiredFieldNotProvidedResponseDto = ResponseDTO.builder()
            .id(53L)
            .responses(Map.of(fieldA.getId(), "123" ))
            .build();

    private static Stream<Arguments> correctResponses() {
        return Stream.of(
                Arguments.of(minCorrectResponseDto),
                Arguments.of(maxCorrectResponseDto)
        );
    }

    private static Stream<Arguments> invalidResponses() {
        return Stream.of(
                Arguments.of(toNonExistingFieldResponseDto, InvalidResponseException.Reason.RESPONSE_TO_INVALID_FIELD),
                Arguments.of(toFieldFromOtherQuestionnaireResponseDto, InvalidResponseException.Reason.RESPONSE_TO_INVALID_FIELD),
                Arguments.of(toNotActiveFieldResponseDto, InvalidResponseException.Reason.RESPONSE_TO_INVALID_FIELD),
                Arguments.of(toNotActiveRequiredFieldResponseDto, InvalidResponseException.Reason.RESPONSE_TO_INVALID_FIELD),
                Arguments.of(requiredFieldNotProvidedResponseDto, InvalidResponseException.Reason.REQUIRED_FIELD_NOT_PROVIDED)
        );
    }


    @ParameterizedTest
    @MethodSource("correctResponses")
    void createResponseShouldPersistCorrectResponseWhenCorrectResponseDtoProvided(ResponseDTO dto) {
        when(questionnaireService.questionnaireExists(QUESTIONNAIRE_ID))
                .thenReturn(true);
        when(fieldsRepository.findByIsActiveAndQuestionnaireId(true, QUESTIONNAIRE_ID))
                .thenReturn(activeFields);
        when(responseRepository.save(any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response answer(InvocationOnMock invocation) throws Throwable {
                        Response response = invocation.getArgument(0);
                        return Response.builder()
                                .id(new Random().nextLong())
                                .questionnaire(response.getQuestionnaire())
                                .responses(response.getResponses())
                                .build();
                    }
                });
        ArgumentCaptor<Response> responseArgumentCaptor = ArgumentCaptor.forClass(Response.class);

        responseService.createResponse(QUESTIONNAIRE_ID, dto);
        verify(responseRepository).save(responseArgumentCaptor.capture());
        Response persisted = responseArgumentCaptor.getValue();

        assertNull(persisted.getId());
        assertEquals(QUESTIONNAIRE_ID, persisted.getQuestionnaire().getId());

        Map<Long, String> persistedResponses = persisted.getResponses().keySet()
                .stream()
                .collect(Collectors.toMap(Field::getId, f -> persisted.getResponses().get(f)));
        assertEquals(dto.getResponses(), persistedResponses);
    }

    @Test
    void createResponseShouldThrowExceptionWhenQuestionnaireNotExists() {
        when(questionnaireService.questionnaireExists(NON_EXISTING_QUESTIONNAIRE_ID))
                .thenReturn(false);

        InvalidResponseException ex = assertThrows(InvalidResponseException.class,
                () -> responseService.createResponse(
                        NON_EXISTING_QUESTIONNAIRE_ID,
                        minCorrectResponseDto
                )
        );

        assertEquals(InvalidResponseException.Reason.QUESTIONNAIRE_NOT_FOUND, ex.getReason());
    }

    @ParameterizedTest
    @MethodSource("invalidResponses")
    void createResponseShouldThrowExceptionWithRightReasonWhenProvidedIncorrectResponseDto(
            ResponseDTO dto, InvalidResponseException.Reason reason
    ) {
        when(questionnaireService.questionnaireExists(QUESTIONNAIRE_ID))
                .thenReturn(true);
        when(fieldsRepository.findByIsActiveAndQuestionnaireId(true, QUESTIONNAIRE_ID))
                .thenReturn(activeFields);

        InvalidResponseException ex = assertThrows(InvalidResponseException.class,
                () -> responseService.createResponse(QUESTIONNAIRE_ID, dto));

        assertEquals(reason, ex.getReason());
    }

    @Test
    void createResponseShouldThrowExceptionWhenProvidedEmptyResponseDto() {
        when(questionnaireService.questionnaireExists(QUESTIONNAIRE_ID))
                .thenReturn(true);

        InvalidResponseException ex = assertThrows(InvalidResponseException.class,
                () -> responseService.createResponse(QUESTIONNAIRE_ID, emptyResponseDto));

        assertEquals(InvalidResponseException.Reason.EMPTY_RESPONSE, ex.getReason());
    }


    @Test
    void getResponsesByQuestionnaireShouldThrowExceptionWhenQuestionnaireNotExists() {
        when(questionnaireService.questionnaireExists(NON_EXISTING_QUESTIONNAIRE_ID))
                .thenReturn(false);

        InvalidResponseException ex = assertThrows(InvalidResponseException.class,
                () -> responseService.getResponsesByQuestionnaire(
                        NON_EXISTING_QUESTIONNAIRE_ID,
                        Pageable.unpaged()
                )
        );

        assertEquals(InvalidResponseException.Reason.QUESTIONNAIRE_NOT_FOUND, ex.getReason());
    }

    @Test
    void deleteResponseShouldThrowExceptionWhenPassedNonExistingId() {
        when(responseRepository.deleteResponseById(NON_EXISTING_RESPONSE_ID))
                .thenReturn(0L);

        InvalidResponseException ex = assertThrows(InvalidResponseException.class,
                () -> responseService.deleteResponse(NON_EXISTING_RESPONSE_ID));

        assertEquals(InvalidResponseException.Reason.NOT_FOUND, ex.getReason());
    }
}