package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldLabelDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldUpdateDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Questionnaire;
import com.github.enid3.questionnaire.data.mapper.FieldMapper;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.service.FieldService;
import com.github.enid3.questionnaire.service.QuestionnaireService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.field.FieldExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldsRepository fieldsRepository;

    private final FieldMapper fieldMapper;
    private final FieldExceptionFactory fieldExceptionFactory;

    private final QuestionnaireService questionnaireService;


    @Override
    @Transactional
    public FieldDTO createField(long questionnaireId, FieldDTO fieldDTO) {
        Field field = fieldMapper.toField(fieldDTO);
        field.setId(null);
        try {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setId(questionnaireId);
            field.setQuestionnaire(questionnaire);
            Field savedField = fieldsRepository.save(field);
            return fieldMapper.toFieldDTO(savedField);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Page<FieldDTO> getAllFieldsByQuestionnaire(long questionnaireId, Pageable pageable) {
        try {
            boolean isQuestionnaireExists = questionnaireService.questionnaireExists(questionnaireId);
            if(isQuestionnaireExists) {
                return fieldsRepository
                        .findAllByQuestionnaireId(questionnaireId, pageable)
                        .map(fieldMapper::toFieldDTO);
            } else {
                throw fieldExceptionFactory.createQuestionnaireNotFoundException(questionnaireId);
            }
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<FieldLabelDTO> getAllActiveFieldLabelsByQuestionnaire(long questionnaireId) {
        try {
            boolean isQuestionnaireExists = questionnaireService.questionnaireExists(questionnaireId);
            if(isQuestionnaireExists) {
                return fieldsRepository
                        .findAllByQuestionnaireIdAndIsActive(questionnaireId, true)
                        .stream()
                        .map(fieldMapper::toFieldLabelDTO)
                        .collect(Collectors.toList());
            } else {
                throw fieldExceptionFactory.createQuestionnaireNotFoundException(questionnaireId);
            }
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<FieldDTO> getAllActiveFieldsByQuestionnaire(long questionnaireId) {
        try {
            boolean isQuestionnaireExists = questionnaireService.questionnaireExists(questionnaireId);
            if(isQuestionnaireExists) {
                return fieldsRepository
                        .findAllByQuestionnaireIdAndIsActive(questionnaireId, true)
                        .stream()
                        .map(fieldMapper::toFieldDTO)
                        .collect(Collectors.toList());
            } else {
                throw fieldExceptionFactory.createQuestionnaireNotFoundException(questionnaireId);
            }
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public FieldDTO updateField(long id, FieldUpdateDTO fieldUpdateDTO) {
        try {
            Optional<Field> fieldOptional = fieldsRepository.findById(id);
            Field field = fieldOptional.orElseThrow(() -> fieldExceptionFactory.createNotFoundException(id));

            fieldMapper.mergeField(field, fieldUpdateDTO);
            fieldsRepository.save(field);

            return fieldMapper.toFieldDTO(field);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteField(long id) {
        long deleted;
        try {
            deleted = fieldsRepository.deleteFieldById(id);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }

        if(deleted == 0) {
            throw fieldExceptionFactory.createNotFoundException(id);
        }
    }
}
