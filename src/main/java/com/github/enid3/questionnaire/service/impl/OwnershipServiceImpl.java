package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.OwnershipService;
import com.github.enid3.questionnaire.service.exception.ServiceException;
import com.github.enid3.questionnaire.service.exception.user.UserExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OwnershipServiceImpl implements OwnershipService {
    private final UserRepository userRepository;

    private final UserExceptionFactory userExceptionFactory;

    @Override
    @PreAuthorize("A.mayUpdateField(principal, #id)")
    @Transactional
    public void setOwner(String email, Field field) {
        try {
            Optional<User> ownerOptional = userRepository.findByEmail(email);
            User owner = ownerOptional.orElseThrow(() -> userExceptionFactory.createUserNotFoundException(email));
            field.setOwner(owner);
        }
        catch (DataAccessException ex) {
            throw new ServiceException(ex);
        }
    }
}
