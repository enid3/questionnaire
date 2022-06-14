package com.github.enid3.questionnaire.data.mapper;

import com.github.enid3.questionnaire.data.dto.questionnaire.*;
import com.github.enid3.questionnaire.data.dto.user.UserInfoDTO;
import com.github.enid3.questionnaire.data.entity.Questionnaire;
import com.github.enid3.questionnaire.data.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface QuestionnaireMapper {
    @Mapping(source = "userInfo", target = "owner")
    Questionnaire toQuestionnaire(QuestionnaireDTO dto);
    Questionnaire toQuestionnaire(QuestionnaireCreateDTO dto);

    @Mapping(source = "owner", target = "userInfo")
    QuestionnaireInfoDTO toQuestionnaireInfoDTO(Questionnaire questionnaire);
    QuestionnaireInfoLightDTO toQuestionnaireInfoLightDTO(Questionnaire questionnaire);

    @Mapping(source = "owner", target = "userInfo")
    QuestionnaireDTO toQuestionnaireDTO(Questionnaire questionnaire);

    UserInfoDTO toUserInfoDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "responses", ignore = true)
    @Mapping(target = "fields", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeQuestionnaire(@MappingTarget Questionnaire questionnaire, QuestionnaireUpdateInfoDTO dto);
}
