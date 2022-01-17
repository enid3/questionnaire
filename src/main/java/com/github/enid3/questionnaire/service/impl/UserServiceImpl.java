package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    //private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public long register(User user) {

        User userFromDB = userRepository.findByEmail(user.getEmail());
        if(userFromDB != null) {
            return userFromDB.getId();
        }


        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return user.getId();
    }
}

