package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.user.auth.ChangePasswordDTO;
import com.github.enid3.questionnaire.data.dto.user.UserResponseDTO;
import com.github.enid3.questionnaire.data.dto.user.UserUpdateDTO;
import com.github.enid3.questionnaire.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping
    public UserResponseDTO updateUserData(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserUpdateDTO userUpdateDTO
    ) {
        return userService.updateUser(userDetails.getUsername(), userUpdateDTO);
    }

    @PostMapping("/changePassword")
    public String chanePassword(
            @RequestBody ChangePasswordDTO changePasswordDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return "" + userService.changeUserPassword(
                userDetails.getUsername(),
                changePasswordDTO.getNewPassword()
                );
    }

}
