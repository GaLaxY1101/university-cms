package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.UserDto;
import com.foxminded.korniichyk.university.dto.registration.UserRegistrationDto;
import com.foxminded.korniichyk.university.model.User;

public interface UserService extends CrudService<User, UserDto> {


    User findByEmail(String username);

    User registerUser(UserRegistrationDto userRegistrationDto);

    boolean isExistsByEmail(String email);
    boolean isExistsByPhoneNumber(String phoneNumber);
}
