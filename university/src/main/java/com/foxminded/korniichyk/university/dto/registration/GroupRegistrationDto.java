package com.foxminded.korniichyk.university.dto.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class GroupRegistrationDto {

    @NotBlank(message = "Please, enter a group's name.")
    private String name;

    private Set<Long> studentsIds;

    @NotNull(message = "Please, choose a speciality.")
    private Long specialityId;
}
