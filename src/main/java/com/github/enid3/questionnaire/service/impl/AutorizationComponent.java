package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.exception.user.UserExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("A")
@RequiredArgsConstructor
public class AutorizationComponent {
    private final FieldsRepository fieldsRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserExceptionFactory userExceptionFactory;

    public boolean mayUpdateField(UserDetails principal, long fieldId){
        // fixme optional & exception
        Field field = fieldsRepository.getById(fieldId);
        User owner = field.getQuestionnaire().getOwner();
        return owner.getEmail().equals(principal.getUsername());
    }

    public boolean mayUpdateUserPassword(String userEmail, String password){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> userExceptionFactory.createUserNotFoundException(userEmail));

        return passwordEncoder.matches(password, user.getPassword());

    }
}
