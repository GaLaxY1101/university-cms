package com.foxminded.korniichyk.university.service.implementation;


import com.foxminded.korniichyk.university.dao.DisciplineDao;
import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.model.Discipline;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
import com.foxminded.korniichyk.university.service.exception.DisciplineNotFoundException;
import com.foxminded.korniichyk.university.mapper.display.DisciplineMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DisciplineServiceImpl implements DisciplineService {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DisciplineServiceImpl.class);

    DisciplineDao disciplineDao;
    DisciplineMapper disciplineMapper;

    public DisciplineServiceImpl(DisciplineDao disciplineDao, DisciplineMapper disciplineMapper) {
        this.disciplineDao = disciplineDao;
        this.disciplineMapper = disciplineMapper;
    }

    @Override
    public DisciplineDto findById(Long id) {
        return disciplineDao.findById(id)
                .map(disciplineMapper::toDto)
                .orElseThrow(() -> new DisciplineNotFoundException("Discipline with id: " + id + " not found"));
    }

    @Override
    public List<DisciplineDto> findAll() {
        return disciplineDao.findAll().stream()
                .map(discipline -> {
                    return disciplineMapper.toDto(discipline);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(Discipline discipline) {
        disciplineDao.save(discipline);
        log.info("{} saved", discipline);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        disciplineDao.findById(id)
                .ifPresentOrElse(
                        discipline -> {
                            disciplineDao.delete(discipline);
                            log.info("{} deleted", discipline);
                        },
                        () -> {
                            throw new DisciplineNotFoundException("Discipline with id: " + id + " not found");
                        });
    }

    @Override
    public Page<DisciplineDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return disciplineDao.findAll(pageable).map(disciplineMapper::toDto);
    }

    @Override
    public Set<Discipline> findAllByIdIn(Set<Long> disciplineIds) {
        return disciplineDao.findAllByIdIn(disciplineIds);
    }
}
