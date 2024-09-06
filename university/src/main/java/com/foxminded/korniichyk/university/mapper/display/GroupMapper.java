package com.foxminded.korniichyk.university.mapper.display;

import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.model.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupDto toDto(Group group);

}
