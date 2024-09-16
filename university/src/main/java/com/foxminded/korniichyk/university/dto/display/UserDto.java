package com.foxminded.korniichyk.university.dto.display;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private LocalDate dateOfBirth;

    private String password;

    private String confirmPassword;

    private Set<RoleDto> roles = new HashSet<>();


}
