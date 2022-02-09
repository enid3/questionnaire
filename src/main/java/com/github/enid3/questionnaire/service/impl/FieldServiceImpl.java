package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldLabelDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldUpdateDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.mapper.FieldMapper;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.service.FieldService;
import com.github.enid3.questionnaire.service.OwnershipService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.field.FieldExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final OwnershipService ownershipService;


    @Override
    @Transactional
    public FieldDTO createField(String ownerEmail, FieldDTO fieldDTO) {
        Field field = fieldMapper.toFieldDTO(fieldDTO);
        field.setId(null);
        try {
            ownershipService.setOwner(ownerEmail, field);
            Field savedField = fieldsRepository.save(field);
            return fieldMapper.toFieldDTO(savedField);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Page<FieldDTO> getAllFieldsByOwner(String ownerEmail, Pageable pageable) {
        try {
            return fieldsRepository
                    .findAllByOwnerEmail(ownerEmail, pageable)
                    .map(fieldMapper::toFieldDTO);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<FieldLabelDTO> getAllLabelsByOwner(String ownerEmail) {
        try {
            return fieldsRepository
                    .findAllByOwnerEmailAndIsActive(ownerEmail, true)
                    .stream()
                    .map(fieldMapper::toFieldLabelDTO)
                    .collect(Collectors.toList());
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    @PreAuthorize("A.mayUpdateField(principal, #id)")
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
    @PreAuthorize("A.mayUpdateField(principal, #id)")
    public void deleteField(long id) {
        int deleted;
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
