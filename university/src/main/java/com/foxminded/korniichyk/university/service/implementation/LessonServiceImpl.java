package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.LessonDao;
import com.foxminded.korniichyk.university.dto.display.LessonDto;
import com.foxminded.korniichyk.university.model.Lesson;
import com.foxminded.korniichyk.university.service.contract.LessonService;
import com.foxminded.korniichyk.university.service.exception.LessonNotFoundException;
import com.foxminded.korniichyk.university.mapper.display.LessonMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class LessonServiceImpl implements LessonService {



    LessonDao lessonDao;
    LessonMapper lessonMapper;

    public LessonServiceImpl(LessonDao lessonDao, LessonMapper lessonMapper) {
        this.lessonDao = lessonDao;
        this.lessonMapper = lessonMapper;
    }

    @Override
    public LessonDto findById(Long id) {
        return lessonDao.findById(id)
                .map(lessonMapper::toDto)
                .orElseThrow(
                        () -> {
                            return new LessonNotFoundException("Lesson with id " + id + " not found");
                        }
                );
    }

    @Transactional
    @Override
    public void save(Lesson lesson) {
        lessonDao.save(lesson);
        log.info("{} saved", lesson);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        lessonDao.findById(id)
                .ifPresentOrElse(
                        lesson -> {
                            lessonDao.delete(lesson);
                            log.info("{} deleted", lesson);
                        },
                        () -> {
                            throw new LessonNotFoundException("Lesson with id " + id + " not found");
                        }
                );
    }

    @Override
    public Page<LessonDto> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return lessonDao.findAll(pageable).map(lessonMapper::toDto);
    }

}
