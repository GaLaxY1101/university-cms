package com.foxminded.korniichyk.university.mapper.update;

import com.foxminded.korniichyk.university.dto.update.StudentUpdateDto;
import com.foxminded.korniichyk.university.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserUpdateMapper.class})
public interface StudentUpdateMapper {


    @Mappings({
            @Mapping(target = "groupId", source = "group.id"),
            @Mapping(target = "user", source = "user")
    })
    StudentUpdateDto toDto(Student student);
}
