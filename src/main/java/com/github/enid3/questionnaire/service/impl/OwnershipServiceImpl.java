package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.entity.Questionnaire;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.OwnershipService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.user.UserExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OwnershipServiceImpl implements OwnershipService {
    private final UserRepository userRepository;

    private final UserExceptionFactory userExceptionFactory;

    @Override
    @Transactional
    public void setOwner(String email, Questionnaire questionnaire) {
        try {
            Optional<User> ownerOptional = userRepository.findByEmail(email);
            User owner = ownerOptional.orElseThrow(() -> userExceptionFactory.createUserNotFoundException(email));
            questionnaire.setOwner(owner);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }
}
