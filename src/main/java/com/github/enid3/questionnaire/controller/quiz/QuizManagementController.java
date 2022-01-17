package com.github.enid3.questionnaire.controller.quiz;

import com.github.enid3.questionnaire.data.dto.FieldDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quiz/manage")
public class QuizManagementController {
    @Autowired
    private FieldsRepository fieldsRepository;

    @PostMapping("showfields")
    public @ResponseBody List<Field> shFields() {
        return fieldsRepository.findAll();
    }


    @GetMapping("addNewField")
    public String addNewField(Model model) {
        model.addAttribute(new FieldDTO());

        return "manageField";
    }

    @GetMapping("edit/{id}")
    public String editField(@PathVariable long id, Model model) {
        Field field = fieldsRepository.getById(id);
        model.addAttribute(new FieldDTO(field));

        return "manageField";
    }

    @PostMapping("saveField")
    public String saveField(FieldDTO fieldDTO) {
        Field field = new Field(fieldDTO);
        /*
            Optional<Field> optionalField = fieldsRepository.findById(field.getId());
            if(optionalField.isPresent()) {
                Field fieldInDB = optionalField.get();
                fieldInDB.setLabel(field.getLabel());
                fieldInDB.setType(field.getType());
                fieldInDB.setIsRequired(field.getIsRequired());
                fieldInDB.setIsActive(field.getIsActive());
                fieldInDB.setOptions(field.getOptions());
                fieldsRepository.save(fieldInDB);
            }
        }
        */
        fieldsRepository.save(field);

        return "redirect:/fields";
    }

    @PostMapping("del/{id}")
    public String delelteField(@PathVariable Long id) {
        Optional<Field> optionalField = fieldsRepository.findById(id);
        if(optionalField.isPresent()) {
            Field field = optionalField.get();
            fieldsRepository.delete(field);
        }
        return "redirect:/fields";
    }

}
