package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.questionnaire.*;
import com.github.enid3.questionnaire.data.entity.Questionnaire;
import com.github.enid3.questionnaire.data.mapper.QuestionnaireMapper;
import com.github.enid3.questionnaire.data.repository.QuestionnaireRepository;
import com.github.enid3.questionnaire.service.OwnershipService;
import com.github.enid3.questionnaire.service.QuestionnaireService;
import com.github.enid3.questionnaire.service.UserService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.questionnaire.QuestionnaireExceptionFactory;
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
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;

    private final QuestionnaireMapper questionnaireMapper;
    private final QuestionnaireExceptionFactory questionnaireExceptionFactory;

    private final OwnershipService ownershipService;
    private final UserService userService;

    @Override
    public QuestionnaireDTO getQuestionnaire(long id) {
        Optional<Questionnaire> optional;
        try {
            optional = questionnaireRepository.findById(id);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
        return optional
                .map(questionnaireMapper::toQuestionnaireDTO)
                .orElseThrow(() -> questionnaireExceptionFactory.createNotFoundException(id));
    }

    @Override
    public QuestionnaireInfoDTO getQuestionnaireInfo(long id) {
        Optional<Questionnaire> optional;
        try {
            optional = questionnaireRepository.findById(id);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
        return optional
                .map(questionnaireMapper::toQuestionnaireInfoDTO)
                .orElseThrow(() -> questionnaireExceptionFactory.createNotFoundException(id));
    }
        /*
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
         */

    @Override
    public QuestionnaireInfoLightDTO createQuestionnaire(String ownerEmail, QuestionnaireCreateDTO questionnaireCreateDTO) {
        Questionnaire questionnaire = questionnaireMapper.toQuestionnaire(questionnaireCreateDTO);
        questionnaire.setId(null);
        try {
            ownershipService.setOwner(ownerEmail, questionnaire);
            Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaire);
            return questionnaireMapper.toQuestionnaireInfoLightDTO(savedQuestionnaire);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<QuestionnaireInfoDTO> getAllQuestionnaireInfoByOwner(long ownerId) {
        try {
            boolean isUserExists = userService.userExists(ownerId);
            if(isUserExists) {
                return questionnaireRepository
                        .findAllByOwnerId(ownerId)
                        .stream()
                        .map(questionnaireMapper::toQuestionnaireInfoDTO)
                        .collect(Collectors.toList());
            } else {
                throw questionnaireExceptionFactory.createUserNotFoundException(ownerId);
            }
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Page<QuestionnaireInfoDTO> getAllReadyQuestionnaires(Pageable pageable) {
        try {
            return questionnaireRepository
                    .findAllReady(pageable)
                    .map(questionnaireMapper::toQuestionnaireInfoDTO);
        }
        catch(DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Page<QuestionnaireInfoDTO> getFeaturedQuestionnaires(long questionnaireId, Pageable pageable) {
        try {
            Questionnaire questionnaire = questionnaireRepository
                    .findById(questionnaireId)
                    .orElseThrow(() -> questionnaireExceptionFactory.createNotFoundException(questionnaireId));
            return questionnaireRepository
                    .findAllReadyByOwnerId(questionnaire.getOwner().getId(), pageable)
                    .map(questionnaireMapper::toQuestionnaireInfoDTO);
        }
        catch (DataAccessException ex) {
            throw  new ServiceException(ex);
        }
    }

    @Override
    public List<QuestionnaireInfoLightDTO> getAllQuestionnaireInfoByOwner(String ownerEmail) {
        try {
            return questionnaireRepository
                    .findAllByOwnerEmail(ownerEmail)
                    .stream()
                    .map(questionnaireMapper::toQuestionnaireInfoLightDTO)
                    .collect(Collectors.toList());
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Page<QuestionnaireInfoDTO> getAllQuestionnaireInfo(Pageable pageable) {
        try {
            return questionnaireRepository
                    .findAll(pageable)
                    .map(questionnaireMapper::toQuestionnaireInfoDTO);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public QuestionnaireInfoLightDTO updateQuestionnaireInfo(long id, QuestionnaireUpdateInfoDTO questionnaireUpdateInfoDTO) {
        try {
            Optional<Questionnaire> optional = questionnaireRepository.findById(id);
            Questionnaire questionnaire = optional.orElseThrow(() -> questionnaireExceptionFactory.createNotFoundException(id));

            questionnaireMapper.mergeQuestionnaire(questionnaire, questionnaireUpdateInfoDTO);
            questionnaireRepository.save(questionnaire);

            return questionnaireMapper.toQuestionnaireInfoLightDTO(questionnaire);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    @Transactional
    public void deleteQuestionnaire(long id) {
        int deleted;
        try {
            deleted = questionnaireRepository.deleteQuestionnaireById(id);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }

        if(deleted == 0) {
            throw questionnaireExceptionFactory.createNotFoundException(id);
        }
    }

    @Override
    public boolean questionnaireExists(long id) {
        try {
            return questionnaireRepository.existsById(id);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }
}
