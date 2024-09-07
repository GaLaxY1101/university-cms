package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.LessonTypeDao;
import com.foxminded.korniichyk.university.dto.display.LessonTypeDto;
import com.foxminded.korniichyk.university.model.LessonType;
import com.foxminded.korniichyk.university.service.contract.LessonTypeService;
import com.foxminded.korniichyk.university.service.exception.LessonTypeNotFoundException;
import com.foxminded.korniichyk.university.mapper.display.LessonTypeMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LessonTypeServiceImpl implements LessonTypeService {

    private final LessonTypeMapper lessonTypeMapper;
    private final LessonTypeDao lessonTypeDao;

    public LessonTypeServiceImpl(LessonTypeDao lessonTypeDao, LessonTypeMapper lessonTypeMapper) {
        this.lessonTypeDao = lessonTypeDao;
        this.lessonTypeMapper = lessonTypeMapper;
    }

    @Transactional
    @Override
    public LessonTypeDto findById(Long id) {
        return lessonTypeDao.findById(id)
                .map(lessonTypeMapper::toDto)
                .orElseThrow(() -> new LessonTypeNotFoundException("Lesson type with id "+ id + "not found"));
    }

    @Transactional
    @Override
    public List<LessonTypeDto> findAll() {
        return lessonTypeDao.findAll()
                .stream()
                .map(lessonTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(LessonType lessonType) {
        lessonTypeDao.save(lessonType);
        log.info("Lesson type {} saved", lessonType);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        var lessonType = lessonTypeDao.findById(id)
                .orElseThrow(() -> new LessonTypeNotFoundException("Lesson type with id "+ id + "not found"));

        lessonTypeDao.delete(lessonType);
        log.info("Lesson type {} deleted", lessonType);
    }

    @Override
    public Page<LessonTypeDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return lessonTypeDao.findAll(pageable).map(lessonTypeMapper::toDto);
    }
}
