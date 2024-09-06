package com.foxminded.korniichyk.university.dto.display;

import lombok.Data;

import java.util.Set;

@Data
public class TeacherDto {
    private Long id;
    private UserDto user;
    private Set<DisciplineDto> disciplines;

}
