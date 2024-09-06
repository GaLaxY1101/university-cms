package com.foxminded.korniichyk.university.dto.display;

import lombok.Data;

@Data
public class StudentDto{

    private Long id;
    private UserDto user;
    private String groupName;

}
