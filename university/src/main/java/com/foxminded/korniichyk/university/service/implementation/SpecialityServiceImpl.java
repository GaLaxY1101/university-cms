package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.SpecialityDao;
import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.service.contract.SpecialityService;
import com.foxminded.korniichyk.university.service.exception.SpecialityNotFoundException;
import com.foxminded.korniichyk.university.mapper.display.SpecialityMapper;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SpecialityServiceImpl implements SpecialityService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SpecialityServiceImpl.class);

    SpecialityDao specialityDao;
    SpecialityMapper specialityMapper;

    public SpecialityServiceImpl(SpecialityDao specialityDao, SpecialityMapper specialityMapper) {
        this.specialityDao = specialityDao;
        this.specialityMapper = specialityMapper;
    }

    @Override
    public SpecialityDto findById(Long id) {
        return specialityDao.findById(id)
                .map(specialityMapper::toDto)
                .orElseThrow(() -> new SpecialityNotFoundException("Speciality with id " + id + " not found"));
    }

    @Override
    public List<SpecialityDto> findAll() {
        return specialityDao.findAll()
                .stream()
                .map(specialityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(Speciality speciality) {
        specialityDao.save(speciality);
        log.info("{} saved", speciality);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        specialityDao.findById(id)
                .ifPresentOrElse(
                        speciality -> {
                            specialityDao.delete(speciality);
                            log.info("{} deleted", speciality);
                        },
                        () -> {
                            throw new SpecialityNotFoundException("Speciality with id " + id + " not found");
                        }
                );
    }

    @Override
    public Page<SpecialityDto> findPage(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return specialityDao.findAll(pageable).map(specialityMapper::toDto);
    }
}
