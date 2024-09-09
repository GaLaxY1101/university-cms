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


    @Test
    void addDiscipline_shouldAddDisciplineToTeacher() {
        Long teacherId = 1L;
        Long disciplineId = 2L;
        Teacher teacher = new Teacher();
        Discipline discipline = new Discipline();

        when(teacherDao.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(disciplineDao.findById(disciplineId)).thenReturn(Optional.of(discipline));

        teacherService.addDiscipline(teacherId, disciplineId);

        assertTrue(teacher.getDisciplines().contains(discipline));
        assertTrue(discipline.getTeachers().contains(teacher));
    }

    @Test
    void addDiscipline_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(TeacherNotFoundException.class, () -> teacherService.addDiscipline(1L, 1L));
    }

    @Test
    void addDiscipline_shouldThrowDisciplineNotFoundException_whenDisciplineDoesNotExist() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.of(new Teacher()));
        when(disciplineDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DisciplineNotFoundException.class, () -> teacherService.addDiscipline(1L, 1L));
    }

    @Test
    void addDiscipline_shouldThrowDisciplineAlreadyAssignedException_whenDisciplineAlreadyAssigned() {
        Long teacherId = 1L;
        Long disciplineId = 2L;
        Teacher teacher = new Teacher();
        Discipline discipline = new Discipline();

        teacher.getDisciplines().add(discipline);

        when(teacherDao.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(disciplineDao.findById(disciplineId)).thenReturn(Optional.of(discipline));


        assertThrows(DisciplineAlreadyAssignedException.class, () -> teacherService.addDiscipline(teacherId, disciplineId));
    }

    @Test
    void removeDiscipline_shouldRemoveDisciplineFromTeacher() {
        Long teacherId = 1L;
        Long disciplineId = 2L;
        Teacher teacher = new Teacher();
        Discipline discipline = new Discipline();

        teacher.getDisciplines().add(discipline);
        discipline.getTeachers().add(teacher);

        when(teacherDao.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(disciplineDao.findById(disciplineId)).thenReturn(Optional.of(discipline));

        teacherService.removeDiscipline(teacherId, disciplineId);

        assertFalse(teacher.getDisciplines().contains(discipline));
        assertFalse(discipline.getTeachers().contains(teacher));
        verify(teacherDao).save(teacher);
    }

    @Test
    void removeDiscipline_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(TeacherNotFoundException.class, () -> teacherService.removeDiscipline(1L, 1L));
    }

    @Test
    void removeDiscipline_shouldThrowDisciplineNotFoundException_whenDisciplineDoesNotExist() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.of(new Teacher()));
        when(disciplineDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DisciplineNotFoundException.class, () -> teacherService.removeDiscipline(1L, 1L));
    }

    @Test
    void removeDiscipline_shouldThrowDisciplineNotFoundException_whenDisciplineNotAssignedToTeacher() {
        Long teacherId = 1L;
        Long disciplineId = 2L;
        Teacher teacher = new Teacher();
        Discipline discipline = new Discipline();

        when(teacherDao.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(disciplineDao.findById(disciplineId)).thenReturn(Optional.of(discipline));

        assertThrows(DisciplineNotFoundException.class, () -> teacherService.removeDiscipline(teacherId, disciplineId));
    }

    @Test
    void addLesson_shouldAddLessonToTeacher() {
        Long teacherId = 1L;
        Long lessonId = 2L;
        Teacher teacher = new Teacher();
        Lesson lesson = new Lesson();

        when(teacherDao.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(lessonDao.findById(lessonId)).thenReturn(Optional.of(lesson));

        teacherService.addLesson(teacherId, lessonId);

        assertTrue(teacher.getLessons().contains(lesson));
        assertTrue(lesson.getTeachers().contains(teacher));
        verify(teacherDao).save(teacher);
    }

    @Test
    void addLesson_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(TeacherNotFoundException.class, () -> teacherService.addLesson(1L, 1L));
    }

    @Test
    void addLesson_shouldThrowLessonNotFoundException_whenLessonDoesNotExist() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.of(new Teacher()));
        when(lessonDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(LessonNotFoundException.class, () -> teacherService.addLesson(1L, 1L));
    }

    @Test
    void addLesson_shouldThrowLessonAlreadyAssignedException_whenLessonAlreadyAssigned() {
        Long teacherId = 1L;
        Long lessonId = 2L;
        Teacher teacher = new Teacher();
        Lesson lesson = new Lesson();

        teacher.getLessons().add(lesson);

        when(teacherDao.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(lessonDao.findById(lessonId)).thenReturn(Optional.of(lesson));

        assertThrows(LessonAlreadyAssignedException.class, () -> teacherService.addLesson(teacherId, lessonId));
    }



    @Test
    void removeLesson_shouldRemoveLessonFromTeacher() {
        Long teacherId = 1L;
        Long lessonId = 2L;
        Teacher teacher = new Teacher();
        Lesson lesson = new Lesson();

        teacher.getLessons().add(lesson);
        lesson.getTeachers().add(teacher);

        when(teacherDao.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(lessonDao.findById(lessonId)).thenReturn(Optional.of(lesson));

        teacherService.removeLesson(teacherId, lessonId);

        assertFalse(teacher.getLessons().contains(lesson));
        assertFalse(lesson.getTeachers().contains(teacher));
        verify(teacherDao).save(teacher);
    }

    @Test
    void removeLesson_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(TeacherNotFoundException.class, () -> teacherService.removeLesson(1L, 1L));
    }

    @Test
    void removeLesson_shouldThrowLessonNotFoundException_whenLessonDoesNotExist() {
        when(teacherDao.findById(anyLong())).thenReturn(Optional.of(new Teacher()));
        when(lessonDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(LessonNotFoundException.class, () -> teacherService.removeLesson(1L, 1L));
    }

    @Test
    void removeLesson_shouldThrowLessonNotFoundException_whenLessonNotAssignedToTeacher() {
        Long teacherId = 1L;
        Long lessonId = 2L;
        Teacher teacher = new Teacher();
        Lesson lesson = new Lesson();

        when(teacherDao.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(lessonDao.findById(lessonId)).thenReturn(Optional.of(lesson));

        assertThrows(LessonNotFoundException.class, () -> teacherService.removeLesson(teacherId, lessonId));
    }


}
