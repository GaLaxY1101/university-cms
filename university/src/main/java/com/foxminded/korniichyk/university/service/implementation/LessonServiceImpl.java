package com.foxminded.korniichyk.university.service.implementation;

import com.foxminded.korniichyk.university.dao.LessonDao;
import com.foxminded.korniichyk.university.dto.display.LessonDto;
import com.foxminded.korniichyk.university.mapper.display.LessonMapper;
import com.foxminded.korniichyk.university.model.Lesson;
import com.foxminded.korniichyk.university.service.contract.LessonService;
import com.foxminded.korniichyk.university.service.exception.LessonNotFoundException;
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
public class LessonServiceImpl implements LessonService {



    private final LessonDao lessonDao;
    private final LessonMapper lessonMapper;


    @Override
    public LessonDto findById(Long id) {
        return lessonDao.findById(id)
                .map(lessonMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Lesson with id {} not found", id);
                    return new LessonNotFoundException("Lesson not found");
                });
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
                            log.error("Lesson with id {} not found", id);
                            throw new LessonNotFoundException("Lesson not found");
                        }
                );
    }


    @Override
    public Page<LessonDto> findPage(Pageable pageable) {
        return lessonDao.findAll(pageable).map(lessonMapper::toDto);
    }

}
