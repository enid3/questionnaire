package com.github.enid3.questionnaire.data.repository;

import com.github.enid3.questionnaire.data.dto.LabelDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FieldsRepository extends JpaRepository<Field, Long> {


    Page<Field> findAllByOwner(Pageable pageable, User owner);
    List<Field> findByIsActive(Boolean isActive);
    List<Field> findByIsActiveAndOwner(Boolean isActive, User owner);

    @Query( name ="select_all_labels", nativeQuery=true)
    List<LabelDTO> findAllLabelsByOwnerId(Long id);


    List<Field> findAllByIdInAndOwner(Iterable<Long> ids, Optional<User> owner);

}
