package com.github.enid3.questionnaire.controller;

import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("logout")
    public String logout() {
        return "redirect:/fields";
    }
}
