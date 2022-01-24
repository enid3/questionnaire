package com.github.enid3.questionnaire.controller.api.v1;

import com.github.enid3.questionnaire.data.entity.Field;
import com.github.enid3.questionnaire.data.entity.Response;
import com.github.enid3.questionnaire.data.repository.FieldsRepository;
import com.github.enid3.questionnaire.data.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/response")
public class ResponseController {
    ResponseRepository responseRepository;
    FieldsRepository fieldsRepository;

    @Autowired
    public ResponseController(ResponseRepository responseRepository,
                              FieldsRepository fieldsRepository) {
        this.responseRepository = responseRepository;
        this.fieldsRepository = fieldsRepository;
    }

    @GetMapping
    public Page<Response> getResponses(
            @RequestParam(defaultValue="0", required=false) Integer page,
            @RequestParam(defaultValue="0", required = false) Integer count
    ) {
        Pageable pageable = Pageable.unpaged();
        if(count != 0) {
            pageable = PageRequest.of(page, count, Sort.by("id").ascending());
        }
        return responseRepository.findAll(pageable);
    }

    @PostMapping
    public Response saveResponse(
            @RequestBody Map<Long, String> responseData,
            Errors errors
    ) {
        Response response = new Response();
        for(Map.Entry<Long, String> entry : responseData.entrySet()) {
            response.getResponses().put(
                   fieldsRepository.getById(entry.getKey()), entry.getValue());

        }

        return responseRepository.save(response);
    }
}
