package com.foxminded.korniichyk.university.mapper.display;

import com.foxminded.korniichyk.university.dto.display.UserDto;
import com.foxminded.korniichyk.university.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

}
