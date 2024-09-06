package com.foxminded.korniichyk.university.dto.update;

import com.foxminded.korniichyk.university.dto.registration.UserRegistrationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentUpdateDto {
    private Long id;

    @Valid
    private UserUpdateDto user;

    @NotNull(message = "Please, choose the group")
    private Long groupId;
}
