package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.questionnaire.QuestionnaireDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.mapper.FieldMapper;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.service.QuestionnaireService;
import com.github.enid3.questionnaire.service.UserService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.questionnaire.QuestionnaireExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final FieldsRepository fieldsRepository;

    private final FieldMapper fieldMapper;
    private final QuestionnaireExceptionFactory questionnaireExceptionFactory;

    private final UserService userService;

    @Override
    public QuestionnaireDTO getQuestionnaireByUser(long userId) {
        try {
            boolean isUserExists = userService.userExists(userId);
            if(isUserExists) {
                List<Field> fields = fieldsRepository.findByIsActiveAndOwnerId(true, userId);
                if(fields.isEmpty()) {
                    throw questionnaireExceptionFactory.createNoActiveFieldsException(userId);
                }
                return QuestionnaireDTO.builder()
                        .fields(fields
                                .stream()
                                .map(fieldMapper::toFieldDTO)
                                .collect(Collectors.toList()))
                        .build();
            } else {
                throw questionnaireExceptionFactory.createUserNotFoundException(userId);
            }
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }
}
