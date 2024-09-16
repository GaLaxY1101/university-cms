package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.model.Discipline;

import java.util.List;
import java.util.Set;

public interface DisciplineService extends CrudService<Discipline, DisciplineDto> {

    Set<Discipline> findAllByIdIn(Set<Long> disciplineIds);

    List<DisciplineDto> findAll();
}
