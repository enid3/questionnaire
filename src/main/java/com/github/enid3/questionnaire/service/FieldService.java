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
    FieldDTO createField(long questionnaireId, @Valid FieldDTO fieldDTO);

    Page<FieldDTO> getAllFieldsByQuestionnaire(long questionnaireId, Pageable pageable);
    List<FieldLabelDTO> getAllActiveFieldLabelsByQuestionnaire(long questionnaireId);
    List<FieldDTO> getAllActiveFieldsByQuestionnaire(long questionnaireId);

    FieldDTO updateField(long id, @Validated FieldUpdateDTO fieldUpdateDTO);


    void deleteField(long id);
}
