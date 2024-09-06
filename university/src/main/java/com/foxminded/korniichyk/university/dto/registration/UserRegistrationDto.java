package com.foxminded.korniichyk.university.dto.registration;

import com.foxminded.korniichyk.university.model.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserRegistrationDto {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^0\\d{9}$", message = "Please provide a valid phone number without country code")
    private String phoneNumber;

    @Email(message = "Please provide a valid email address")
    private String email;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Password is required")
    private String password;

    private String confirmPassword;

    private Set<Role> roles = new HashSet<>();

    @AssertTrue(message = "Passwords must match")
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(confirmPassword);
    }
}
