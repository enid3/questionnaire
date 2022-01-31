package com.github.enid3.questionnaire.service;

import com.github.enid3.questionnaire.data.entity.User;

public interface UserService {
    boolean register(User user);
    boolean update(String email, User newUserData);
    boolean changePassword(String email, String oldPassword, String newPassword);
}
