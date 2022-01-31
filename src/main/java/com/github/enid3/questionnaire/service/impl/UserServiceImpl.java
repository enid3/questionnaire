package com.github.enid3.questionnaire.service.impl;

import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import com.github.enid3.questionnaire.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        }
        throw new UsernameNotFoundException("User with email: '" + email + "' not found.");
    }

    @Override
    public boolean register(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());
        if(userFromDB != null) {
            return false;
        }

        /*
        String message = String.format("Hello, %s!\n" +
                "You have successfully registered on the site: http://localhost:8080/", user.getFirstName());
        send(user.getEmail(), "Notification", message);
         */

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean update(String email, User newUserData) {
        User user = userRepository.findByEmail(email);
        log.info("changing {}: {}",email, newUserData );
        if(newUserData.getFirstName() != null)
            user.setFirstName(newUserData.getFirstName());
        if(newUserData.getLastName() != null)
            user.setLastName(newUserData.getLastName());
        if(newUserData.getEmail() != null)
            user.setEmail(newUserData.getEmail());
        if(newUserData.getPhoneNumber() != null)
            user.setPhoneNumber(newUserData.getPhoneNumber());

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if(passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            }
        }
        throw new UsernameNotFoundException("User with email: '" + email + "' not found.");
    }
}

