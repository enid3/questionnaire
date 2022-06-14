package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.response.ResponseDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Questionnaire;
import com.github.enid3.questionnaire.data.entity.Response;
import com.github.enid3.questionnaire.data.mapper.ResponseMapper;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.ResponseRepository;
import com.github.enid3.questionnaire.service.QuestionnaireService;
import com.github.enid3.questionnaire.service.ResponseService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.response.ResponseExceptionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final ResponseRepository responseRepository;
    private final FieldsRepository fieldsRepository;

    private final ResponseMapper responseMapper;
    private final ResponseExceptionFactory responseExceptionFactory;

    private final QuestionnaireService questionnaireService;

    @Override
    @Transactional
    public ResponseDTO createResponse(long questionnaireId, ResponseDTO responseDTO) {
        try {
            boolean isQuestionnaireExists = questionnaireService.questionnaireExists(questionnaireId);
            if(isQuestionnaireExists) {
                Map<Long, String> responses = responseDTO.getResponses();
                if(responses.isEmpty()) {
                    throw responseExceptionFactory.createEmptyResponseException();
                }
                List<Field> fields = fieldsRepository.findByIsActiveAndQuestionnaireId(true, questionnaireId);
                long[] fieldsSortedKeys = fields.stream()
                        .mapToLong(Field::getId)
                        .sorted()
                        .toArray();

                Optional<Field> requiredNotProvidedOptional = fields.stream()
                        .filter(Field::getIsRequired)
                        .filter(f -> !responses.containsKey(f.getId()))
                        .findAny();

                if(requiredNotProvidedOptional.isPresent()) {
                    throw responseExceptionFactory.createRequiredFieldNotProvidedException(requiredNotProvidedOptional.get().getId());
                }

                Optional<Long> temp = responses.keySet().stream()
                        .filter(id -> Arrays.binarySearch(fieldsSortedKeys, id) < 0)
                        .findAny();

                if(temp.isPresent()) {
                    throw responseExceptionFactory.createResponseToInvalidFieldException(temp.get());
                }

                Response response = Response
                        .builder()
                        .questionnaire(
                                Questionnaire.builder()
                                        .id(questionnaireId)
                                        .build()
                        )
                        .responses(fields
                            .stream()
                            .filter(f -> responses.containsKey(f.getId()))
                            .collect(Collectors.toMap(f -> f, f -> responses.get(f.getId())))
                        )
                        .build();

                return responseMapper.toResponseDTO(responseRepository.save(response));
            } else {
                throw responseExceptionFactory.createQuestionnaireNotFoundException(questionnaireId);
            }
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Page<ResponseDTO> getResponsesByQuestionnaire(long questionnaireId, Pageable pageable) {
        boolean isQuestionnaireExists = questionnaireService.questionnaireExists(questionnaireId);
        if(!isQuestionnaireExists) {
            throw responseExceptionFactory.createQuestionnaireNotFoundException(questionnaireId);
        }
        try {
            return responseRepository
                    .findDistinctByQuestionnaireId(questionnaireId, pageable)
                    .map(responseMapper::toResponseDTO);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean deleteResponseByFieldId(long id) {
        long deletedResponsesCount = 0;
        try {
            List<Response> responsesToDelete = responseRepository.findDistinctByFieldId(id);
            log.info("responses to delete: {}", responsesToDelete);
            deletedResponsesCount = responseRepository.deleteAllByIdIn(responsesToDelete.stream()
                    .map(Response::getId)
                    .collect(Collectors.toList())
            );


        }
            catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }

        if(deletedResponsesCount == 0) {
            throw responseExceptionFactory.createRequiredFieldNotProvidedException(id);
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteResponse(long id) {
        long deleted;
        try {
            deleted = responseRepository.deleteResponseById(id);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }

        if(deleted == 0) {
            throw responseExceptionFactory.createNotFoundException(id);
        }
    }

}
