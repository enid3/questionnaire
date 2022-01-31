package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.LabelDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/field")
@Slf4j
public class FieldController {
    private FieldsRepository fieldsRepository;
    @Autowired
    private UserRepository userRepository;


    @Autowired
    public FieldController(FieldsRepository fieldsRepository){
        this.fieldsRepository = fieldsRepository;
    }

    @GetMapping
    public Page<Field> getFields(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue="0", required=false) Integer page,
            @RequestParam(defaultValue="0", required = false) Integer count
    ) {
        Pageable pageable = Pageable.unpaged();
        if(count != 0) {
            pageable = PageRequest.of(page, count, Sort.by("id").ascending());
        }
        log.info("User details: {}", userDetails);
        User owner = userRepository.findByEmail(userDetails.getUsername());
        return fieldsRepository.findAllByOwner(pageable, owner);
    }

    //@GetMapping("/{id}")
    public Field getField(
            @PathVariable Long id
    ) {
        return  fieldsRepository.getById(id);
    }

    @GetMapping("/label")
    public List<LabelDTO> getLabels(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User owner = userRepository.findByEmail(userDetails.getUsername());
        return fieldsRepository.findAllLabelsByOwnerId(owner.getId());
    }

    @PostMapping
    public Field saveField(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Field field,
            Errors errors
    ) {
        User owner = userRepository.findByEmail(userDetails.getUsername());
        if(field.getId() == null || (owner.getId().equals(field.getId()))) {
            if(errors.hasErrors()) {
                throw new RuntimeException("has errors");
            }
            field.setOwner(owner);
            return fieldsRepository.save(field);
        }
        throw new BadCredentialsException("not owner of field");
    }

    @DeleteMapping("/{id}")
    public void deleteField(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        User owner = userRepository.findByEmail(userDetails.getUsername());
        Field fieldToDelete = fieldsRepository.getById(id);
        if(owner.getId().equals(fieldToDelete.getId())) {
            fieldsRepository.deleteById(id);
        }
        throw new BadCredentialsException("not owner of field");
    }

}
