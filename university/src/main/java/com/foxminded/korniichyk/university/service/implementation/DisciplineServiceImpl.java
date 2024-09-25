package com.foxminded.korniichyk.university.service.implementation;


import com.foxminded.korniichyk.university.dao.DisciplineDao;
import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.mapper.display.DisciplineMapper;
import com.foxminded.korniichyk.university.model.Discipline;
import com.foxminded.korniichyk.university.projection.input.NameProjection;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
import com.foxminded.korniichyk.university.service.exception.DisciplineNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineDao disciplineDao;
    private final DisciplineMapper disciplineMapper;


    @Override
    public DisciplineDto findById(Long id) {
        return disciplineDao.findById(id)
                .map(disciplineMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Discipline with id {} not found", id);
                    return new DisciplineNotFoundException("Discipline not found");
                });
    }


    @Override
    public List<DisciplineDto> findAll() {
        return disciplineDao.findAll().stream()
                .map(discipline -> {
                    return disciplineMapper.toDto(discipline);
                })
                .collect(toList());
    }

    @Override
    public List<NameProjection> findAllDisciplineOptions() {
        return disciplineDao.findAllDisciplineOptions();
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
                            log.info("Discipline with id {} deleted", id);
                        },
                        () -> {
                            log.error("Discipline with id {} not found", id);
                            throw new DisciplineNotFoundException("Discipline not found");
                        }
                );
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
