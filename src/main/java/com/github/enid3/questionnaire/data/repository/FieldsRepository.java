package com.github.enid3.questionnaire.data.repository;

import com.github.enid3.questionnaire.data.entity.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FieldsRepository extends JpaRepository<Field, Long> {

    @Query(value="select * from fields where is_active = 'True'", nativeQuery = true)
    List<Field> findActive();

    List<Field> findByIsActive(Boolean isActive);

}
