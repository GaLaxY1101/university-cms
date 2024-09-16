package com.foxminded.korniichyk.university.mapper.display;

import com.foxminded.korniichyk.university.dto.display.LessonDto;
import com.foxminded.korniichyk.university.model.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {

   LessonDto toDto(Lesson lesson);

}
