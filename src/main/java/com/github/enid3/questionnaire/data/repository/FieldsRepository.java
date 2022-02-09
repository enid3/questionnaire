package com.github.enid3.questionnaire.data.repository;

import com.github.enid3.questionnaire.data.entity.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldsRepository extends JpaRepository<Field, Long> {
    Page<Field> findAllByOwnerEmail(String ownerEmail, Pageable pageable);
    List<Field> findAllByOwnerEmailAndIsActive(String ownerEmail, Boolean isActive);
    List<Field> findByIsActiveAndOwnerId(Boolean isActive, Long ownerId);

    boolean existsByIdAndOwnerEmail(Long id, String ownerEmail);

    int deleteFieldById(Long id);
}
