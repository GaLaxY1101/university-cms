package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.model.Discipline;
import com.foxminded.korniichyk.university.projection.input.NameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface DisciplineService extends CrudService<Discipline, DisciplineDto>, SearchableByName<DisciplineDto> {

    Set<Discipline> findAllByIdIn(Set<Long> disciplineIds);

    List<DisciplineDto> findAll();

    List<NameProjection> findAllDisciplineOptions();

    Page<DisciplineDto> findAllByTeacherId(Long teacherId, Pageable pageable);
}
