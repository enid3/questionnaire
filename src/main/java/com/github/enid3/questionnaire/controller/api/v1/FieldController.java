package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/field")
public class FieldController {
    private FieldsRepository fieldsRepository;

    @Autowired
    public FieldController(FieldsRepository fieldsRepository){
        this.fieldsRepository = fieldsRepository;
    }

    @GetMapping
    public Page<Field> getFields(
            @RequestParam(defaultValue="0", required=false) Integer page,
            @RequestParam(defaultValue="0", required = false) Integer count
    ) {
        Pageable pageable = Pageable.unpaged();
        if(count != 0) {
            pageable = PageRequest.of(page, count, Sort.by("id").ascending());
        }
        return fieldsRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Field getField(
            @PathVariable Long id
    ) {
        return  fieldsRepository.getById(id);
    }

    @PostMapping
    public Field saveField(
            @Valid @RequestBody Field field,
            Errors errors
    ) {
        if(errors.hasErrors()) {
            throw new RuntimeException("has errors");
        }
        return fieldsRepository.save(field);
    }

    @DeleteMapping("/{id}")
    public void deleteField(
            @PathVariable Long id
    ) {
        fieldsRepository.deleteById(id);
    }

}
