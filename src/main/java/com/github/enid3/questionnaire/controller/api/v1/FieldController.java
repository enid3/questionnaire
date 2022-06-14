package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldUpdateDTO;
import com.github.enid3.questionnaire.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
public class FieldController {
    private final FieldService fieldService;

    @PutMapping("/{id}")
    public FieldDTO updateField(
            @PathVariable long id,
            @RequestBody FieldUpdateDTO fieldUpdateDTO
    ) {
        return fieldService.updateField(id, fieldUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteField(
            @PathVariable long id
    ) {
        fieldService.deleteField(id);
    }

}
