package com.foxminded.korniichyk.university.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GroupUpdateDto {

    private Long id;

    @NotBlank(message = "Pleas, enter a group name.")
    private String name;

    @NotNull(message = "Please, choose a speciality.")
    private Long SpecialityId;

}
