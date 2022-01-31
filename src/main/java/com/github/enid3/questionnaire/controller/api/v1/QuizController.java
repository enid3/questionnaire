package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.ResponseRepository;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/questionnaire")
public class QuizController {
    private FieldsRepository fieldsRepository;
    @Autowired
    private UserRepository userRepository;
    private ResponseRepository responseRepository;

    @Autowired
    public QuizController(FieldsRepository fieldsRepository, ResponseRepository responseRepository) {
        this.fieldsRepository = fieldsRepository;
        this.responseRepository = responseRepository;
    }

    @GetMapping("/{id}")
    public List<Field> getQuestionnaire(
            @PathVariable Long id
    ) throws Exception {
        Optional<User> ownerOprional = userRepository.findById(id);
        if(ownerOprional.isPresent()) {
            return fieldsRepository.findByIsActiveAndOwner(true,ownerOprional.get());
        }
        throw new Exception("wrong user id");
    }
}
