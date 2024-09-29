package com.foxminded.korniichyk.university.service.contract;

import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.dto.option.SpecialityOptionDto;
import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.projection.input.NameProjection;

import java.util.List;

public interface SpecialityService extends CrudService<Speciality, SpecialityDto>, SearchableByName<SpecialityDto> {

    List<SpecialityOptionDto> findAllSpecialityOptions();

    Speciality findReferenceById(Long id);
    NameProjection findSpecialityOptionById(Long id);

}
