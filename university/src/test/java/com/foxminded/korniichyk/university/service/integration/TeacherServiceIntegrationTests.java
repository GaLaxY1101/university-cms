package com.foxminded.korniichyk.university.service.integration;

import com.foxminded.korniichyk.university.TestContainerConfig;
import com.foxminded.korniichyk.university.dao.TeacherDao;
import com.foxminded.korniichyk.university.model.*;
import com.foxminded.korniichyk.university.service.contract.DisciplineService;
import com.foxminded.korniichyk.university.service.contract.LessonService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@ContextConfiguration(classes = {TestContainerConfig.class})
@Sql(scripts = {"/db/scripts/clean_tables.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TeacherServiceIntegrationTests {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private UserService userService;

    @Test
    void deleteTeacher_shouldDeleteTeacher_whenTeacherExists() {
        var teacherId = insertTeacher();

        teacherService.delete(teacherId);
        assertEquals(0, teacherService.findAll().size());

    }

    @Test
    void deleteTeacher_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        assertThrows(TeacherNotFoundException.class, () -> teacherService.delete(1L));
    }

    @Test
    void addDiscipline_shouldAddDiscipline_whenDisciplineAndTeacherExist() {
        var teacherId = insertTeacher();

        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");

        Discipline discipline2 = new Discipline();
        discipline2.setName("English");
        discipline2.setDescription("English course");

        disciplineService.save(discipline);
        disciplineService.save(discipline2);

        teacherService.addDiscipline(teacherId, discipline.getId());
        teacherService.addDiscipline(teacherId, discipline2.getId());

        assertEquals(2, teacherService.findById(teacherId).getDisciplines().size());


    }

    @Test
    void addDiscipline_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        assertThrows(TeacherNotFoundException.class, () -> teacherService.addDiscipline(1L, 1L));
    }

    @Test
    void addDiscipline_shouldThrowDisciplineNotFoundException_whenDisciplineDoesNotExist() {
        var teacherId = insertTeacher();

        assertThrows(DisciplineNotFoundException.class, () -> teacherService.addDiscipline(teacherId, 1L));
    }

    @Test
    void addDiscipline_shouldThrowDisciplineAlreadyAssignedException_whenDisciplineAlreadyAssignedToTheTeacher() {
        var teacherId = insertTeacher();


        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        teacherService.addDiscipline(teacherId, discipline.getId());

        assertThrows(DisciplineAlreadyAssignedException.class, () -> teacherService.addDiscipline(teacherId, discipline.getId()));
    }


    @Test
    void removeDiscipline_shouldRemoveDiscipline_whenDisciplineExistsInTeachersList() {
        var teacherId = insertTeacher();

        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");

        Discipline discipline2 = new Discipline();
        discipline2.setName("English");
        discipline2.setDescription("English course");

        disciplineService.save(discipline);
        disciplineService.save(discipline2);

        teacherService.addDiscipline(teacherId, discipline.getId());
        teacherService.addDiscipline(teacherId, discipline2.getId());


        teacherService.removeDiscipline(teacherId, discipline.getId());
        teacherService.removeDiscipline(teacherId, discipline2.getId());
        assertEquals(0, teacherService.findById(teacherId).getDisciplines().size());
    }

    @Test
    void removeDiscipline_shouldThrowDisciplineNotFoundException_whenDisciplineDoesNotExist() {
        var teacherId = insertTeacher();

        assertThrows(DisciplineNotFoundException.class, () -> teacherService.removeDiscipline(teacherId, 1L));
    }

    @Test
    void removeDiscipline_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        assertThrows(TeacherNotFoundException.class, () -> teacherService.removeDiscipline(2L, discipline.getId()));
    }


    @Test
    void removeDiscipline_shouldThrowDisciplineNotFoundException_whenDisciplineNotIncludedInTeachersList() {
        var teacherId = insertTeacher();

        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        assertThrows(DisciplineNotFoundException.class, () -> teacherService.removeDiscipline(teacherId, discipline.getId()));
    }

    @Test
    void addLesson_shouldAddLessonToTeacher_whenLessonAndTeacherExists() {
        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        var teacherId = insertTeacher();

        LessonType lessonType = new LessonType();
        lessonType.setName("Lecture");

        Lesson lesson = new Lesson();
        lesson.setDiscipline(discipline);
        lesson.setLessonType(lessonType);
        lesson.setDate(LocalDate.now());
        lesson.setStartTime(LocalTime.of(8, 30));
        lesson.setEndTime(LocalTime.of(10, 25));
        lesson.setClassroomNumber(101);
        lessonService.save(lesson);

        teacherService.addLesson(teacherId, lesson.getId());
        assertEquals(1, teacherDao.findById(teacherId).get().getLessons().size());
    }

    @Test
    void addLesson_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        LessonType lessonType = new LessonType();
        lessonType.setName("Lecture");

        Lesson lesson = new Lesson();
        lesson.setDiscipline(discipline);
        lesson.setLessonType(lessonType);
        lesson.setDate(LocalDate.now());
        lesson.setStartTime(LocalTime.of(8, 30));
        lesson.setEndTime(LocalTime.of(10, 25));
        lesson.setClassroomNumber(101);
        lessonService.save(lesson);

        assertThrows(TeacherNotFoundException.class, () -> teacherService.addLesson(1L, lesson.getId()));
    }

    @Test
    void addLesson_shouldThrowLessonNotFoundException_whenLessonDoesNotExist() {
        Long teacherId = insertTeacher();

        assertThrows(LessonNotFoundException.class, () -> teacherService.addLesson(teacherId, 1L));
    }

    @Test
    void addLesson_shouldThrowLessonAlreadyAssignedException_whenLessonAlreadyAssignedToTeacher() {
        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        var teacherId = insertTeacher();

        LessonType lessonType = new LessonType();
        lessonType.setName("Lecture");


        Lesson lesson = new Lesson();
        lesson.setDiscipline(discipline);
        lesson.setLessonType(lessonType);
        lesson.setDate(LocalDate.now());
        lesson.setStartTime(LocalTime.of(8, 30));
        lesson.setEndTime(LocalTime.of(10, 25));
        lesson.setClassroomNumber(101);
        lessonService.save(lesson);

        teacherService.addLesson(teacherId, lesson.getId());
        assertThrows(LessonAlreadyAssignedException.class, () -> teacherService.addLesson(teacherId, lesson.getId()));

    }


    @Test
    void removeLesson_shouldRemoveLessonFromTeacher_whenLessonAssignedToTeacher() {
        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        LessonType lessonType = new LessonType();
        lessonType.setName("Lecture");

        Lesson lesson = new Lesson();
        lesson.setDiscipline(discipline);
        lesson.setLessonType(lessonType);
        lesson.setDate(LocalDate.now());
        lesson.setStartTime(LocalTime.of(8, 30));
        lesson.setEndTime(LocalTime.of(10, 25));
        lesson.setClassroomNumber(101);
        lessonService.save(lesson);

        var teacherId = insertTeacher();

        teacherService.addLesson(teacherId, lesson.getId());
        teacherService.removeLesson(teacherId, lesson.getId());
        assertEquals(0, teacherDao.findById(teacherId).get().getLessons().size());
    }

    @Test
    void removeLesson_shouldThrowLessonNotFoundException_whenLessonDoesNotExist() {
        var teacherId = insertTeacher();

        assertThrows(LessonNotFoundException.class, () -> teacherService.removeLesson(teacherId, 1L));
    }

    @Test
    void removeLesson_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        LessonType lessonType = new LessonType();
        lessonType.setName("Lecture");

        Lesson lesson = new Lesson();
        lesson.setDiscipline(discipline);
        lesson.setLessonType(lessonType);
        lesson.setDate(LocalDate.now());
        lesson.setStartTime(LocalTime.of(8, 30));
        lesson.setEndTime(LocalTime.of(10, 25));
        lesson.setClassroomNumber(101);
        lessonService.save(lesson);

        assertThrows(TeacherNotFoundException.class, () -> teacherService.removeLesson(1L, discipline.getId()));
    }

    @Test
    void removeLesson_shouldThrowLessonNotFoundException_whenLessonDoesntAssignedToTeacher() {
        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setDescription("Math course");
        disciplineService.save(discipline);

        var teacherId = insertTeacher();

        LessonType lessonType = new LessonType();
        lessonType.setName("Lecture");

        Lesson lesson = new Lesson();
        lesson.setDiscipline(discipline);
        lesson.setLessonType(lessonType);
        lesson.setDate(LocalDate.now());
        lesson.setStartTime(LocalTime.of(8, 30));
        lesson.setEndTime(LocalTime.of(10, 25));
        lesson.setClassroomNumber(101);
        lessonService.save(lesson);


        assertThrows(LessonNotFoundException.class, () -> teacherService.removeLesson(teacherId, lesson.getId()));

    }


    private Long insertTeacher() {
        Teacher teacher = new Teacher();
        User user = new User();

        user.setFirstName("Max");
        user.setLastName("White");
        user.setEmail("max@gmail.com");
        user.setPhoneNumber("123456789");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userService.save(user);
        teacher.setUser(user);
        teacherService.save(teacher);
        return teacher.getId();
    }


}
