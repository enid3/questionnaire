package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldLabelDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface FieldService {
    FieldDTO createField(String ownerEmail, @Valid FieldDTO fieldDTO);

    Page<FieldDTO> getAllFieldsByOwner(String ownerEmail, Pageable pageable);
    List<FieldLabelDTO> getAllLabelsByOwner(String ownerEmail);

    FieldDTO updateField(long id, @Validated FieldUpdateDTO fieldUpdateDTO);


    void deleteField(long id);
}
