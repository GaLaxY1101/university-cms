package com.foxminded.korniichyk.university.dto.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class GroupRegistrationDto {

    private Long id;

    @NotBlank(message = "Please, enter a group's name.")
    private String name;


    private Set<Long> studentsIds;

    @NotNull(message = "Please, choose a speciality.")
    private Long specialityId;
}
