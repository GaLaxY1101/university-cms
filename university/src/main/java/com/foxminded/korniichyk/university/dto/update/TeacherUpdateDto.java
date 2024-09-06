package com.foxminded.korniichyk.university.dto.update;

import com.foxminded.korniichyk.university.dto.registration.UserRegistrationDto;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TeacherUpdateDto {

    private Long id;

    @Valid
    private UserUpdateDto user;

    private Set<Long> disciplineIds = new HashSet<>();
}


