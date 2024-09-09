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
import static org.mockito.Mockito.verify;

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

        teacherService.deleteById(teacherId);
        verify(teacherService).deleteById(teacherId);

    }

    @Test
    void deleteTeacher_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        assertThrows(TeacherNotFoundException.class, () -> teacherService.deleteById(1L));
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
