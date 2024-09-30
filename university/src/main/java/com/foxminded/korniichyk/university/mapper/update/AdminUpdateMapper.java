package com.foxminded.korniichyk.university.mapper.update;

import com.foxminded.korniichyk.university.dto.update.AdminUpdateDto;
import com.foxminded.korniichyk.university.model.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserUpdateMapper.class})
public interface AdminUpdateMapper {

    Admin toEntity(AdminUpdateDto adminUpdateDto);

    AdminUpdateDto toDto(Admin admin);

}
