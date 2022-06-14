package com.github.enid3.questionnaire.data.repository;

import com.github.enid3.questionnaire.data.entity.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    List<Questionnaire> findAllByOwnerEmail(String ownerEmail);
    List<Questionnaire> findAllByOwnerId(Long id);
    @Query("select q from Questionnaire q join q.fields f where f.isActive = true and q.owner.id = :id group by q having count(f) >= 1")
    Page<Questionnaire> findAllReadyByOwnerId(@Param("id") Long id, Pageable pageable);
    @Query("select q from Questionnaire q join q.fields f where f.isActive = true group by q having count(f) >= 1")
    Page<Questionnaire> findAllReady(Pageable pageable);


    int deleteQuestionnaireById(Long id);
}
