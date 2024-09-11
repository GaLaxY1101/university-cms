package com.foxminded.korniichyk.university.service.unit;

import com.foxminded.korniichyk.university.dao.LessonDao;
import com.foxminded.korniichyk.university.dto.display.LessonDto;
import com.foxminded.korniichyk.university.model.Lesson;
import com.foxminded.korniichyk.university.service.exception.LessonNotFoundException;
import com.foxminded.korniichyk.university.service.implementation.LessonServiceImpl;
import com.foxminded.korniichyk.university.mapper.display.LessonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTests {

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Mock
    private LessonDao lessonDao;

    @Mock
    private LessonMapper lessonMapper;

    @Test
    void findById_shouldReturnLessonDto_whenLessonExists() {
        Long lessonId = 1L;
        Lesson lesson = new Lesson();
        LessonDto lessonDto = new LessonDto();

        when(lessonDao.findById(anyLong())).thenReturn(Optional.of(lesson));
        when(lessonMapper.toDto(any(Lesson.class))).thenReturn(lessonDto);

        LessonDto result = lessonService.findById(lessonId);

        verify(lessonDao).findById(lessonId);
        verify(lessonMapper).toDto(lesson);
        assertEquals(lessonDto, result);
    }

    @Test
    void findById_shouldThrowLessonNotFoundException_whenLessonDoesNotExist() {
        Long lessonId = 1L;

        when(lessonDao.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.findById(lessonId));
    }

    @Test
    void delete_shouldDeleteLesson_whenLessonExists() {
        Long lessonId = 1L;
        Lesson lesson = new Lesson();

        when(lessonDao.findById(anyLong())).thenReturn(Optional.of(lesson));

        lessonService.deleteById(lessonId);

        verify(lessonDao).findById(lessonId);
        verify(lessonDao).delete(lesson);
    }

    @Test
    void delete_shouldThrowLessonNotFoundException_whenLessonDoesNotExist() {
        Long lessonId = 1L;

        when(lessonDao.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> lessonService.deleteById(lessonId));
    }

}
