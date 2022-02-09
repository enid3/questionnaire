package com.github.enid3.questionnaire.data.repository;


import com.github.enid3.questionnaire.data.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    @Query(value = "select r from Response r join r.responses resp  where (key(resp).owner.email = :ownerEmail)")
    Page<Response> findAllByFieldOwnerEmail(@Param("ownerEmail") String ownerEmail, Pageable pageable);
}