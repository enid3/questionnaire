package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Response;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questionnaire")
public class QuizController {
    private FieldsRepository fieldsRepository;
    private ResponseRepository responseRepository;

    @Autowired
    public QuizController(FieldsRepository fieldsRepository, ResponseRepository responseRepository) {
        this.fieldsRepository = fieldsRepository;
        this.responseRepository = responseRepository;
    }

    @GetMapping
    public List<Field> getQuestionnaire() {
        return fieldsRepository.findByIsActive(true);
    }
}
