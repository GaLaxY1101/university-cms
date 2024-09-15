package com.foxminded.korniichyk.university.util;

import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.dto.display.UserDto;
import com.foxminded.korniichyk.university.dto.registration.UserRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.UserUpdateDto;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.model.User;

import java.time.LocalDate;

public class TestUtil {

    public static UserDto generateUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Admin");
        userDto.setLastName("User");
        userDto.setEmail("admin@gmail.com");
        userDto.setPhoneNumber("0999999999");
        userDto.setDateOfBirth(LocalDate.of(2000, 2, 2));

        return userDto;
    }

    public static DisciplineDto generateDisciplineDto() {
        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("Name");
        disciplineDto.setDescription("Description");
        return disciplineDto;
    }

    public static GroupDto generateGroupDto() {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(1L);
        groupDto.setName("Name");
        return groupDto;
    }

    public static UserRegistrationDto generateValidUserRegistrationDto() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("email@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));
        return userRegistrationDto;
    }

    public static UserRegistrationDto generateInvalidUserRegistrationDto() {

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("");
        userRegistrationDto.setLastName("");
        userRegistrationDto.setEmail("badEmail");
        userRegistrationDto.setPassword("");
        userRegistrationDto.setConfirmPassword("");
        userRegistrationDto.setPhoneNumber("");

        return userRegistrationDto;
    }

    public static UserUpdateDto generateValidUserUpdateDto() {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setEmail("email@gmail.com");
        userUpdateDto.setPhoneNumber("0999999999");
        userUpdateDto.setFirstName("test");
        userUpdateDto.setLastName("test");
        userUpdateDto.setDateOfBirth(LocalDate.of(2000,2,2));
        userUpdateDto.setId(1L);
        return userUpdateDto;
    }

    public static SpecialityDto generateSpecialityDto() {
        SpecialityDto specialityDto = new SpecialityDto();
        specialityDto.setCode(121);
        specialityDto.setName("Name");
        specialityDto.setId(1L);
        specialityDto.setDescription("Description");

        return specialityDto;
    }

    public static User generateUser() {
        User user = new User();
        user.setFirstName("Max");
        user.setLastName("White");
        user.setEmail("maxw@gmail.com");
        user.setPhoneNumber("122456789");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        return user;
    }

    public static Speciality generateSpeciality() {
        Speciality speciality = new Speciality();
        speciality.setCode(121);
        speciality.setName("Software engineering");
        return speciality;
    }

    public static Group generateGroup() {
        Group group = new Group();
        group.setName("Group 1");
        return group;
    }
}
