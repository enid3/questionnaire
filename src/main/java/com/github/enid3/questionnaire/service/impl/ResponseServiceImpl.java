package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.response.ResponseDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Response;
import com.github.enid3.questionnaire.data.mapper.ResponseMapper;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.ResponseRepository;
import com.github.enid3.questionnaire.service.ResponseService;
import com.github.enid3.questionnaire.service.UserService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.response.ResponseExceptionFactory;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final ResponseRepository responseRepository;
    private final FieldsRepository fieldsRepository;

    private final ResponseMapper responseMapper;
    private final ResponseExceptionFactory responseExceptionFactory;

    private final UserService userService;

    @Override
    @Transactional
    public ResponseDTO createResponse(long ownerId, ResponseDTO responseDTO) {
        try {
            boolean isUserExists = userService.userExists(ownerId);
            if(isUserExists) {
                Map<Long, String> responses = responseDTO.getResponses();
                if(responses.isEmpty()) {
                    throw responseExceptionFactory.createEmptyResponseException();
                }
                List<Field> fields = fieldsRepository.findByIsActiveAndOwnerId(true, ownerId);
                long[] fieldsSortedKeys = fields.stream()
                        .mapToLong(Field::getId)
                        .sorted()
                        .toArray();

                Optional<Field> requiredNotProvidedOptional = fields.stream()
                        .filter(Field::getIsRequired)
                        .filter(f -> !responses.containsKey(f.getId()))
                        .findAny();

                if(requiredNotProvidedOptional.isPresent()) {
                    throw responseExceptionFactory.createNoSuchFieldException(requiredNotProvidedOptional.get().getId());
                }

                Optional<Long> temp = responses.keySet().stream()
                        .filter(id -> Arrays.binarySearch(fieldsSortedKeys, id) < 0)
                        .findAny();

                if(temp.isPresent()) {
                    throw responseExceptionFactory.createResponseToInvalidFieldException(temp.get());
                }

                Response response = Response
                        .builder()
                            .responses(fields
                                .stream()
                                .filter(f -> responses.containsKey(f.getId()))
                                .collect(Collectors.toMap(f -> f, f -> responses.get(f.getId())))
                        )
                        .build();

                return responseMapper.toResponseDTO(responseRepository.save(response));
            } else {
                throw responseExceptionFactory.createUserNotFoundException(ownerId);
            }
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Page<ResponseDTO> getResponsesByOwner(String ownerEmail, Pageable pageable) {
        try {
            return responseRepository
                    .findAllByFieldOwnerEmail(ownerEmail, pageable)
                    .map(responseMapper::toResponseDTO);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }
}
