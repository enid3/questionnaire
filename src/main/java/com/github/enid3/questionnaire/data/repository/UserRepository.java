package com.github.enid3.questionnaire.data.repository;

import com.github.enid3.questionnaire.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsById(long id);
}
