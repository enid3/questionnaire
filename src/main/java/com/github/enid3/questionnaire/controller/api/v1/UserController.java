package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.ChangePasswordDTO;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @PutMapping
    public String updateUserData(@RequestBody User newUserData,
                                 @AuthenticationPrincipal UserDetails userDetails
    ) {
        return "" + userService.update(userDetails.getUsername(), newUserData);
    }
    @PostMapping("/changePassword")
    public String chanePassword(
            @RequestBody ChangePasswordDTO changePasswordDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return "" + userService.changePassword(
                userDetails.getUsername(),
                changePasswordDTO.getOldPassword(),
                changePasswordDTO.getNewPassword()
                );
    }

}
