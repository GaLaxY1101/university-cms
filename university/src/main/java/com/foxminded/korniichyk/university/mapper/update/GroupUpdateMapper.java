package com.foxminded.korniichyk.university.mapper.update;

import com.foxminded.korniichyk.university.dto.update.GroupUpdateDto;
import com.foxminded.korniichyk.university.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GroupUpdateMapper {

    @Mappings({
            @Mapping(target = "specialityId", source = "speciality.id"),
    })
    GroupUpdateDto toDto(Group group);

}
