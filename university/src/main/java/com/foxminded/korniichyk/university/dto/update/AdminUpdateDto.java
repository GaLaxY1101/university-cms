package com.foxminded.korniichyk.university.dto.update;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class AdminUpdateDto {

    private Long id;

    @Valid
    UserUpdateDto user;

}
