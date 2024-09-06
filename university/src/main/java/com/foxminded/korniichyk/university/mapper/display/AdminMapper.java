package com.foxminded.korniichyk.university.mapper.display;

import com.foxminded.korniichyk.university.dto.display.AdminDto;
import com.foxminded.korniichyk.university.model.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDto toDto(Admin admin);

}
