package com.foxminded.korniichyk.university.mapper.display;

import com.foxminded.korniichyk.university.dto.display.RoleDto;
import com.foxminded.korniichyk.university.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);

}
