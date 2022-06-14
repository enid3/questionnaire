package com.github.enid3.questionnaire.data.mapper;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.questionnaire.QuestionnaireCreateDTO;
import com.github.enid3.questionnaire.data.dto.questionnaire.QuestionnaireDTO;
import com.github.enid3.questionnaire.data.dto.questionnaire.QuestionnaireInfoDTO;
import com.github.enid3.questionnaire.data.dto.questionnaire.QuestionnaireInfoLightDTO;
import com.github.enid3.questionnaire.data.dto.user.UserInfoDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Questionnaire;
import com.github.enid3.questionnaire.data.entity.Response;
import com.github.enid3.questionnaire.data.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionnaireMapperTest {
    private static final QuestionnaireMapper questionnaireMapper = Mappers.getMapper(QuestionnaireMapper.class);
    private static final FieldMapper fieldMapper = Mappers.getMapper(FieldMapper.class);

    private static final List<Field> fields = List.of(
            Field.builder()
                    .id(1L)
                    .label("singleline r a")
                    .type(Field.Type.SINGLE_LINE_TEXT)
                    .isRequired(true)
                    .isActive(true)
                    .build(),
            Field.builder()
                    .id(2L)
                    .label("multiline a")
                    .type(Field.Type.MULTILINE_TEXT)
                    .isRequired(false)
                    .isActive(false)
                    .build(),
            Field.builder()
                    .id(3L)
                    .label("checkboxes a")
                    .type(Field.Type.CHECKBOX)
                    .options(Set.of("A", "B", "C"))
                    .isRequired(false)
                    .isActive(true)
                    .build()
    );
    private static final User owner = User.builder()
            .id(9L)
            .firstName("firstname")
            .lastName("laaastname")
            .email("email@eml.gov")
            .build();

    private static final Questionnaire questionnaire = Questionnaire.builder()
            .id(12312L)
            .name("some name")
            .description("some long long long description")
            .fields(fields)
            .owner(owner)
            .responses(List.of(Response.builder().id(30L).build(), Response.builder().id(31L).build()))
            .build();


    private final static UserInfoDTO userInfoDTO = UserInfoDTO.builder()
            .id(owner.getId())
            .firstName(owner.getFirstName())
            .lastName(owner.getLastName())
            .build();
    private final static QuestionnaireDTO questionnaireDTO = QuestionnaireDTO.builder()
            .id(questionnaire.getId())
            .name(questionnaire.getName())
            .description(questionnaire.getDescription())
            .userInfo(userInfoDTO)
            .fields(fields.stream()
                    .map(fieldMapper::toFieldDTO)
                    .collect(Collectors.toList()))
            .build();

    private final static QuestionnaireCreateDTO questionnaireCreateDTO = QuestionnaireCreateDTO.builder()
            .name(questionnaire.getName())
            .description(questionnaire.getDescription())
            .build();

    public static void assertUserInfoCorrect(UserInfoDTO userInfoDTO, User user) {
        assertEquals(user.getId(), userInfoDTO.getId());
        assertEquals(user.getFirstName(), userInfoDTO.getFirstName());
        assertEquals(user.getLastName(), userInfoDTO.getLastName());
    }

    @Test
    void toQuestionnaireInfoDTOShouldReturnCorrectDTO() {
        QuestionnaireInfoDTO dto = questionnaireMapper.toQuestionnaireInfoDTO(questionnaire);

        assertEquals(questionnaire.getId(), dto.getId());
        assertEquals(questionnaire.getName(), dto.getName());
        assertEquals(questionnaire.getDescription(), dto.getDescription());

        assertUserInfoCorrect(dto.getUserInfo(), questionnaire.getOwner());
    }

    @Test
    void toQuestionnaireInfoLightDTOShouldReturnCorrectDTO() {
        QuestionnaireInfoLightDTO dto = questionnaireMapper.toQuestionnaireInfoLightDTO(questionnaire);

        assertEquals(questionnaire.getId(), dto.getId());
        assertEquals(questionnaire.getName(), dto.getName());
        assertEquals(questionnaire.getDescription(), dto.getDescription());
    }

    @Test
    void toQuestionnaireDTOShouldReturnCorrectDTO() {
        QuestionnaireDTO dto = questionnaireMapper.toQuestionnaireDTO(questionnaire);

        assertEquals(questionnaire.getId(), dto.getId());
        assertEquals(questionnaire.getName(), dto.getName());
        assertEquals(questionnaire.getDescription(), dto.getDescription());

        assertUserInfoCorrect(dto.getUserInfo(), questionnaire.getOwner());

        Set<FieldDTO> fieldDTOS = questionnaire.getFields().stream()
                        .map(fieldMapper::toFieldDTO)
                        .collect(Collectors.toSet());
        assertEquals(fieldDTOS, new HashSet<>(dto.getFields()));
    }

    @Test
    void toQuestionnaireShouldReturnCorrectDTOWhenPassedCorrectDTO() {
        Questionnaire q = questionnaireMapper.toQuestionnaire(questionnaireDTO);

        assertEquals(questionnaireDTO.getId(), q.getId());
        assertEquals(questionnaireDTO.getName(), q.getName());
        assertEquals(questionnaireDTO.getDescription(), q.getDescription());

        assertEquals(userInfoDTO.getId(), q.getOwner().getId());
        assertEquals(userInfoDTO.getFirstName(), q.getOwner().getFirstName());
        assertEquals(userInfoDTO.getLastName(), q.getOwner().getLastName());

        Set<Field> fields = questionnaireDTO.getFields().stream()
                .map(fieldMapper::toField)
                .collect(Collectors.toSet());
        assertEquals(fields, new HashSet<>(q.getFields()));
    }

    @Test
    void toQuestionnaireShouldReturnCorrectDTOWhenPassedCorrectCreatedDTO() {
        Questionnaire q = questionnaireMapper.toQuestionnaire(questionnaireCreateDTO);

        assertNull(q.getId());

        assertEquals(questionnaireDTO.getName(), q.getName());
        assertEquals(questionnaireDTO.getDescription(), q.getDescription());

        assertNotNull(q.getFields());
        assertEquals(0, q.getFields().size());
    }
}
