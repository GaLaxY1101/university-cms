package com.foxminded.korniichyk.university.projection.edit.group;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StudentProjection {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

}
