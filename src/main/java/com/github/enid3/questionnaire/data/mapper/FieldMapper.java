package com.github.enid3.questionnaire.data.mapper;

import com.github.enid3.questionnaire.data.dto.field.FieldDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldLabelDTO;
import com.github.enid3.questionnaire.data.dto.field.FieldUpdateDTO;
import com.github.enid3.questionnaire.data.entity.Field;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    Field toFieldDTO(FieldDTO fieldDTO);
    FieldDTO toFieldDTO(Field field);
    FieldLabelDTO toFieldLabelDTO(Field field);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeField(@MappingTarget Field field, FieldUpdateDTO fieldUpdateDTO);
}
