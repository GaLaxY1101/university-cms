package com.foxminded.korniichyk.university.mapper.display;

import com.foxminded.korniichyk.university.dto.display.LessonTypeDto;
import com.foxminded.korniichyk.university.model.LessonType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonTypeMapper {

    LessonTypeDto toDto(LessonType lessonType);

}
