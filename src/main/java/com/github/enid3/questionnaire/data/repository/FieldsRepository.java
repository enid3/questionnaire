package com.github.enid3.questionnaire.data.repository;

import com.github.enid3.questionnaire.data.entity.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldsRepository extends JpaRepository<Field, Long> {
    Page<Field> findAllByQuestionnaireId(Long questionnaireId, Pageable pageable);
    List<Field> findAllByQuestionnaireIdAndIsActive(Long questionnaireId, Boolean isActive);
    List<Field> findByIsActiveAndQuestionnaireId(Boolean isActive, Long questionnaire);

    boolean existsByIdAndQuestionnaireId(Long id, Long questionnaireId);

    long deleteFieldById(Long id);
}
