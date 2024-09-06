package com.foxminded.korniichyk.university.mapper.update;

import com.foxminded.korniichyk.university.dto.update.AdminUpdateDto;
import com.foxminded.korniichyk.university.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserUpdateMapper.class})

public interface AdminUpdateMapper {

    @Mapping(target = "user", source = "user")
    Admin toEntity(AdminUpdateDto adminUpdateDto);

    @Mapping(target = "user", source = "user")
    AdminUpdateDto toDto(Admin admin);

}
