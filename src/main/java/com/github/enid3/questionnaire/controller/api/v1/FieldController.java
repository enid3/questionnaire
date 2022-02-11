package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldLabelDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldUpdateDTO;
import com.github.enid3.questionnaire.service.FieldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/field")
@Slf4j
@RequiredArgsConstructor
public class FieldController {
    private final FieldService fieldService;

    @GetMapping
    public Page<FieldDTO> getAllFields(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {
        //throw new ServiceException("some message");
        return fieldService.getAllFieldsByOwner(userDetails.getUsername(), pageable);
    }

    @GetMapping("/label")
    public List<FieldLabelDTO> getAllLabels(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return fieldService.getAllLabelsByOwner(userDetails.getUsername());
    }

    @PostMapping
    public FieldDTO createField(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody FieldDTO fieldDTO
    ) {
        return fieldService.createField(userDetails.getUsername(), fieldDTO);
    }

    @PutMapping("/{id}")
    public FieldDTO updateField(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable long id,
            @RequestBody FieldUpdateDTO fieldUpdateDTO
    ) {
        return fieldService.updateField(id, fieldUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteField(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable long id
    ) {
        fieldService.deleteField(id);
    }

}
