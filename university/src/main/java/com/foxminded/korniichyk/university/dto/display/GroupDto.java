package com.foxminded.korniichyk.university.dto.display;

import lombok.Data;

import java.util.Set;

@Data
public class GroupDto {

    private Long id;
    private String name;
    private SpecialityDto speciality;
    private Set<StudentDto> students;
}
