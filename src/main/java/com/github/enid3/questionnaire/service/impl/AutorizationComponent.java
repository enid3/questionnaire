package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.exception.user.InvalidUserException;
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

    public boolean mayUpdateField(UserDetails principal, long fieldId){
        boolean res = fieldsRepository.existsByIdAndOwnerEmail(fieldId, principal.getUsername());
        return res;
    }

    public boolean mayUpdateUserPassword(String userEmail, String password){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException("no such user", InvalidUserException.Reason.USER_NOT_FOUND, 0l));

        return passwordEncoder.matches(password, user.getPassword());

    }
}
