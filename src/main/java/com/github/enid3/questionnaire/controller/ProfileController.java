package com.github.enid3.questionnaire.controller;

import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("edit")
    public String editProfile(User newUserData) {
        User user = userRepository.getById(newUserData.getId());

        user.setEmail(newUserData.getEmail());
        user.setFirstName(newUserData.getFirstName());
        user.setLastName(newUserData.getLastName());
        user.setPhoneNumber(newUserData.getPhoneNumber());

        return "redirect:/editProfile";
    }
}
