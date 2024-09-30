package com.foxminded.korniichyk.university.dto.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GroupRegistrationDto {

    @NotBlank(message = "Please, enter a group's name.")
    private String name;

    @NotNull(message = "Please, choose a speciality.")
    private Long specialityId;
}
