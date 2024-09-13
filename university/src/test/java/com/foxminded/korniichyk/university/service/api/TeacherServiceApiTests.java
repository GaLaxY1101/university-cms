package com.foxminded.korniichyk.university.service.api;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@Transactional
@SpringBootTest
@ContextConfiguration(classes = {TestContainerConfig.class})
@Sql(scripts = {"/db/scripts/clean_tables.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TeacherServiceApiTests {

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
    void deleteTeacher_shouldThrowTeacherNotFoundException_whenTeacherDoesNotExist() {
        assertThrows(TeacherNotFoundException.class, () -> teacherService.deleteById(1L));
    }
}
