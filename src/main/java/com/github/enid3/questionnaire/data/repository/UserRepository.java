package com.github.enid3.questionnaire.data.repository;

import com.github.enid3.questionnaire.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByFirstName(String firstName);
    User findByEmail(String email);
}
