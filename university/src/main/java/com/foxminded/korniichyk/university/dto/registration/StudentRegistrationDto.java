package com.foxminded.korniichyk.university.dto.registration;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class StudentRegistrationDto {

    private Long id;

    @Valid
    private UserRegistrationDto user;

    private Long groupId;

}
