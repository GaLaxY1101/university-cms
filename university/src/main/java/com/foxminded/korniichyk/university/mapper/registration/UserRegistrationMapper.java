package com.foxminded.korniichyk.university.mapper.registration;

import com.foxminded.korniichyk.university.dto.registration.UserRegistrationDto;
import com.foxminded.korniichyk.university.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    User toEntity(UserRegistrationDto userRegistrationDto);
}
