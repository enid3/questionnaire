package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.dto.ResponseDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Response;
import com.github.enid3.questionnaire.data.entity.User;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.ResponseRepository;
import com.github.enid3.questionnaire.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/response")
public class ResponseController {
    ResponseRepository responseRepository;
    @Autowired
    UserRepository userRepository;
    FieldsRepository fieldsRepository;

    @Autowired
    public ResponseController(ResponseRepository responseRepository,
                              FieldsRepository fieldsRepository) {
        this.responseRepository = responseRepository;
        this.fieldsRepository = fieldsRepository;
    }

    @GetMapping
    public Page<ResponseDTO> getResponses(
            @RequestParam(defaultValue="0", required=false) Integer page,
            @RequestParam(defaultValue="0", required = false) Integer count
    ) {
        Pageable pageable = Pageable.unpaged();
        if(count != 0) {
            pageable = PageRequest.of(page, count, Sort.by("id").ascending());
        }
        List<Response> responseList = responseRepository.findAll();
        List<ResponseDTO> responseDTOS =
                responseList.stream()
                        .map(ResponseDTO::new)
                        .collect(Collectors.toList());
        if(pageable.isPaged()) {
            int start = (int)pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), responseDTOS.size());
            return new PageImpl<>(responseDTOS.subList(start, end), pageable, responseDTOS.size());
        } else {
            return new PageImpl<>(responseDTOS, pageable, responseDTOS.size());
        }
    }

    @PostMapping("/{id}")
    public Response saveResponse(
            @PathVariable Long id,
            @RequestBody Map<Long, String> responseData,
            Errors errors
    ) throws Exception {
        Response response = new Response();
        Optional<User> owner = userRepository.findById(id);
        if(owner.isPresent()) {
            List<Field> fields = fieldsRepository.findAllByIdInAndOwner(responseData.keySet(), owner);
            for (Field field : fields) {
                response.getResponses().put(field, responseData.get(field.getId()));
            }
            return responseRepository.save(response);
        } else {
            throw new Exception("No such owner");
        }
    }
}
