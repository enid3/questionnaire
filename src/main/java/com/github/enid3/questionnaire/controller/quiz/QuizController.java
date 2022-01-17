package com.github.enid3.questionnaire.controller.quiz;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class QuizController {
    @Autowired
    private FieldsRepository fieldsRepository;

    @GetMapping("/fields")
    public String showFields (Model model) {
        List<Field> fields = fieldsRepository.findAll();
        model.addAttribute(fields);

        return "fields";
    }
}
