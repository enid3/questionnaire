package com.github.enid3.questionnaire.data.repository;


import com.github.enid3.questionnaire.data.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    @Query(value = "select DISTINCT(r) from Response r join r.responses resp  where (key(resp).questionnaire.id = :questionnaireId)")
    Page<Response> findDistinctByQuestionnaireId(@Param("questionnaireId") long questionnaireId, Pageable pageable);

    @Query(value = "select DISTINCT(r) from Response r join r.responses resp  where (key(resp).id= :id)")
    List<Response> findDistinctByFieldId(@Param("id") long id);

    long deleteAllByIdIn(List<Long> ids);
    long deleteResponseById(Long id);
}