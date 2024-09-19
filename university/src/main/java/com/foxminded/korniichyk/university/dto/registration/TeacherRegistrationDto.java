package com.foxminded.korniichyk.university.dto.registration;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TeacherRegistrationDto {

    private Long id;

    @Valid
    private UserRegistrationDto user;

    private Set<Long> disciplineIds = new HashSet<>();

}
