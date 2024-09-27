package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.LessonTypeDao;
import com.foxminded.korniichyk.university.dto.display.LessonTypeDto;
import com.foxminded.korniichyk.university.mapper.display.LessonTypeMapper;
import com.foxminded.korniichyk.university.model.LessonType;
import com.foxminded.korniichyk.university.service.contract.LessonTypeService;
import com.foxminded.korniichyk.university.service.exception.LessonTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonTypeServiceImpl implements LessonTypeService {

    private final LessonTypeMapper lessonTypeMapper;
    private final LessonTypeDao lessonTypeDao;


    @Override
    public LessonTypeDto findById(Long id) {
        return lessonTypeDao.findById(id)
                .map(lessonTypeMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Lesson type with id {} not found", id);
                    return new LessonTypeNotFoundException("Lesson type not found");
                });
    }


    @Transactional
    @Override
    public void save(LessonType lessonType) {
        lessonTypeDao.save(lessonType);
        log.info("Lesson type {} saved", lessonType);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        var lessonType = lessonTypeDao.findById(id)
                .orElseThrow(() -> {
                    log.error("Lesson type with id {} not found", id);
                    return new LessonTypeNotFoundException("Lesson type not found");
                });

        lessonTypeDao.delete(lessonType);
        log.info("Lesson type {} deleted", lessonType);
    }


    @Override
    public Page<LessonTypeDto> findPage(Pageable pageable) {
        return lessonTypeDao.findAll(pageable).map(lessonTypeMapper::toDto);
    }

    @Override
    public Page<LessonTypeDto> findByName(String search, Pageable pageable) {
        return lessonTypeDao.findByNameContainingIgnoreCase(search, pageable).map(lessonTypeMapper::toDto);
    }
}
