package com.foxminded.korniichyk.university.mapper.display;

import com.foxminded.korniichyk.university.dto.display.TeacherDto;
import com.foxminded.korniichyk.university.model.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherDto toDto(Teacher teacher);

}
