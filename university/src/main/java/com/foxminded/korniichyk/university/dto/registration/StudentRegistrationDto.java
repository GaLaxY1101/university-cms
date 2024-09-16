package com.foxminded.korniichyk.university.dto.registration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentRegistrationDto {

    private Long id;

    @Valid
    private UserRegistrationDto user;

    @NotNull(message = "Please, choose the group")
    private Long groupId;

}
