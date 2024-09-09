package com.foxminded.korniichyk.university.service.unit;

import com.foxminded.korniichyk.university.dao.DisciplineDao;
import com.foxminded.korniichyk.university.dao.LessonDao;
import com.foxminded.korniichyk.university.dao.TeacherDao;
import com.foxminded.korniichyk.university.dto.display.TeacherDto;
import com.foxminded.korniichyk.university.model.Discipline;
import com.foxminded.korniichyk.university.model.Lesson;
import com.foxminded.korniichyk.university.model.Teacher;
import com.foxminded.korniichyk.university.service.exception.*;
import com.foxminded.korniichyk.university.service.implementation.TeacherServiceImpl;
import com.foxminded.korniichyk.university.mapper.display.TeacherMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceUnitTests {


    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Mock
    private TeacherDao teacherDao;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private DisciplineDao disciplineDao;

    @Mock
    private LessonDao lessonDao;

    @Test
    void findById_shouldReturnTeacher() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.of(new Teacher()));
        when(teacherMapper.toDto(any(Teacher.class))).thenReturn(new TeacherDto());

        teacherService.findById(1L);

        verify(teacherDao).findById(anyLong());

    }

    @Test
    void findById_shouldThrowTeacherNotFoundException_whenTeacherNotFound() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class, () -> teacherService.findById(1L));
    }

    @Test
    void delete_shouldDeleteTeacher_whenTeacherExists() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.of(new Teacher()));
        doNothing().when(teacherDao).delete(any(Teacher.class));

        teacherService.deleteById(1L);

        verify(teacherDao).delete(any(Teacher.class));
    }

    @Test
    void delete_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(TeacherNotFoundException.class, () -> teacherService.deleteById(1L));
    }





}
