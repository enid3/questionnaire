package com.github.enid3.questionnaire.data.repository;


import com.github.enid3.questionnaire.data.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}