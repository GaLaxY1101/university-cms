package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.SpecialityDao;
import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.dto.option.SpecialityOptionDto;
import com.foxminded.korniichyk.university.mapper.display.SpecialityMapper;
import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.service.contract.SpecialityService;
import com.foxminded.korniichyk.university.service.exception.SpecialityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityDao specialityDao;
    private final SpecialityMapper specialityMapper;
    private final GroupDao groupDao;

    @Override
    public SpecialityDto findById(Long id) {
        return specialityDao.findById(id)
                .map(specialityMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Speciality with id {} not found", id);
                    return new SpecialityNotFoundException("Speciality not found");
                });
    }


    @Transactional
    @Override
    public void save(Speciality speciality) {
        specialityDao.save(speciality);
        log.info("{} saved", speciality);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Speciality speciality = specialityDao.findById(id)
                .orElseThrow(() -> {
                    log.error("Speciality with id {} not found", id);
                    return new SpecialityNotFoundException("Speciality not found");
                });

        specialityDao.delete(speciality);
        log.info("{} deleted", speciality);
    }


    @Override
    public Page<SpecialityDto> findPage(Pageable pageable) {
        return specialityDao.findAll(pageable).map(specialityMapper::toDto);
    }

    @Override
    public List<SpecialityOptionDto> findAllSpecialityOptions() {
        return specialityDao.findAllSpecialityOptions().stream().map((projection) ->
                        new SpecialityOptionDto(
                                projection.getId(),
                                projection.getName(),
                                projection.getCode()
                        )
                )
                .collect(toList());
    }

    @Override
    public Speciality findReferenceById(Long id) {
        return specialityDao.findReferenceById(id);
    }

}
