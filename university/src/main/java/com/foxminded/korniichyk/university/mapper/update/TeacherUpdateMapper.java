package com.foxminded.korniichyk.university.mapper.update;

import com.foxminded.korniichyk.university.dto.update.TeacherUpdateDto;
import com.foxminded.korniichyk.university.model.Discipline;
import com.foxminded.korniichyk.university.model.Teacher;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper(componentModel = "spring", uses = {UserUpdateMapper.class})
public abstract class TeacherUpdateMapper {

    @Autowired
    private DisciplineService disciplineService;


    @Mapping(source = "disciplines", target = "disciplineIds", qualifiedByName = "mapDisciplinesToIds")
    public abstract TeacherUpdateDto toDto(Teacher teacher);

    @Named("mapDisciplinesToIds")
    Set<Long> mapDisciplineToIds(Set<Discipline> disciplines) {
        return  disciplines
                .stream()
                .map(Discipline::getId)
                .collect(toSet());
    }

    @Named("mapIdsToDisciplines")
    Set<Discipline> mapIdsToDisciplines(Set<Long> disciplineIds) {
        return disciplineService.findAllByIdIn(disciplineIds);
    }
}
