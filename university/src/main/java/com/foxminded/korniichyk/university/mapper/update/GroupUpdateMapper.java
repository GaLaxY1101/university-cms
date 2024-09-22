package com.foxminded.korniichyk.university.mapper.update;

import com.foxminded.korniichyk.university.dto.update.GroupUpdateDto;
import com.foxminded.korniichyk.university.model.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupUpdateMapper {

    GroupUpdateDto toDto(Group group);

}
