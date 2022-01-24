package com.github.enid3.questionnaire.controller.quiz;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
