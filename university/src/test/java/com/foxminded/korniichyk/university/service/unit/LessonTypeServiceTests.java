package com.foxminded.korniichyk.university.service.unit;

import com.foxminded.korniichyk.university.dao.LessonTypeDao;
import com.foxminded.korniichyk.university.dto.display.LessonTypeDto;
import com.foxminded.korniichyk.university.model.LessonType;
import com.foxminded.korniichyk.university.service.exception.LessonTypeNotFoundException;
import com.foxminded.korniichyk.university.service.implementation.LessonTypeServiceImpl;
import com.foxminded.korniichyk.university.mapper.display.LessonTypeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LessonTypeServiceTests {

    @InjectMocks
    private LessonTypeServiceImpl lessonTypeService;

    @Mock
    private LessonTypeDao lessonTypeDao;

    @Mock
    private LessonTypeMapper lessonTypeMapper;

    @Test
    void findById_shouldReturnLessonType() {
        when(lessonTypeDao.findById(anyLong())).thenReturn(Optional.of(new LessonType()));
        when(lessonTypeMapper.toDto(any(LessonType.class))).thenReturn(new LessonTypeDto());

        lessonTypeService.findById(1L);

        verify(lessonTypeDao).findById(1L);
    }

    @Test
    void findById_shouldThrowLessonTypeNotFoundException_whenLessonTypeNotFound() {
        when(lessonTypeDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(LessonTypeNotFoundException.class, () -> lessonTypeService.findById(1L));
    }

    @Test
    void delete_shouldDeleteLessonType_ifExists() {
        when(lessonTypeDao.findById(anyLong())).thenReturn(Optional.of(new LessonType()));

        lessonTypeService.deleteById(1L);

        verify(lessonTypeDao).findById(anyLong());
        verify(lessonTypeDao).delete(any(LessonType.class));
    }

    @Test
    void delete_shouldThrowLessonTypeNotFoundException_whenLessonTypeNotFound() {
        when(lessonTypeDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(LessonTypeNotFoundException.class, () -> lessonTypeService.deleteById(1L));
    }

    @Test
    void save_shouldSaveLessonType() {
        LessonType lessonType = new LessonType();

        lessonTypeService.save(lessonType);

        verify(lessonTypeDao).save(lessonType);
    }


}
