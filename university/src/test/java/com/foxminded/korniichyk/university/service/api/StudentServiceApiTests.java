package com.foxminded.korniichyk.university.service.api;

import com.foxminded.korniichyk.university.TestContainerConfig;
import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.SpecialityService;
import com.foxminded.korniichyk.university.service.contract.StudentService;
import com.foxminded.korniichyk.university.service.contract.UserService;
import com.foxminded.korniichyk.university.service.exception.GroupNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import com.foxminded.korniichyk.university.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = {TestContainerConfig.class})
@Sql(scripts = {"/db/scripts/clean_tables.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class StudentServiceApiTests {


    @Autowired
    private StudentService studentService;

    @Autowired
    private GroupService groupService;

    @Autowired
    SpecialityService specialityService;

    @Autowired
    UserService userService;


    @Test
    void assignGroup_shouldAssignGroupToStudent() {
        Speciality speciality = TestUtil.generateSpeciality();
        specialityService.save(speciality);

        Group group = TestUtil.generateGroup();
        groupService.save(group);


        Student student = new Student();
        User user = TestUtil.generateUser();
        userService.save(user);
        studentService.save(student);
        student.setUser(user);

        studentService.assignGroup(group.getId(), student.getId());

        StudentDto result = studentService.findById(student.getId());
        assertEquals(result.getGroupName(), group.getName());
    }

    @Test
    void assignGroup_shouldThrowGroupNotFoundException_whenGroupDoesNotExist() {
        Student student = new Student();
        User user = TestUtil.generateUser();

        userService.save(user);
        studentService.save(student);
        student.setUser(user);

        assertThrows(GroupNotFoundException.class, () -> studentService.assignGroup(1L, student.getId()));

    }

    @Test
    void assignGroup_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {
        Speciality speciality = TestUtil.generateSpeciality();

        specialityService.save(speciality);

        Group group = TestUtil.generateGroup();
        groupService.save(group);

        assertThrows(StudentNotFoundException.class, () -> studentService.assignGroup(group.getId(), 1L));

    }


    @Test
    void findByGroupIdExcludingByStudentId_shouldReturnStudents_whenGroupHasStudents() {
        Speciality speciality = TestUtil.generateSpeciality();

        specialityService.save(speciality);

        Group group = TestUtil.generateGroup();

        groupService.save(group);

        User user1 = TestUtil.generateUser();

        userService.save(user1);

        User user2 = new User();
        user2.setFirstName("Max2");
        user2.setLastName("White2");
        user2.setEmail("max112@gmail.com");
        user2.setPhoneNumber("122456711");
        user2.setDateOfBirth(LocalDate.of(2001, 1, 1));
        userService.save(user2);

        Student student1 = new Student();
        studentService.save(student1);
        student1.setUser(user1);


        Student student2 = new Student();
        studentService.save(student2);
        student2.setUser(user2);


        studentService.assignGroup(group.getId(), student1.getId());
        studentService.assignGroup(group.getId(), student2.getId());

        Pageable pageable = PageRequest.of(0, 10);
        assertEquals(1, studentService.findByGroupIdExcludingByStudentId(group.getId(), student1.getId(), pageable).getContent().size());
    }

    @Test
    void findByGroupIdExcludingByStudentId_shouldThrowGroupNotFoundException_whenGroupDoesNotExist() {

        Pageable pageable = PageRequest.of(1, 10);
        assertThrows(GroupNotFoundException.class, () -> studentService.findByGroupIdExcludingByStudentId(1L, 1L, pageable));
    }

    @Test
    void findStudentsByGroupName_shouldReturnStudents_whenGroupHasStudents() {
        Speciality speciality = TestUtil.generateSpeciality();

        specialityService.save(speciality);

        Group group = TestUtil.generateGroup();

        groupService.save(group);


        Student student = new Student();
        User user = TestUtil.generateUser();

        userService.save(user);
        studentService.save(student);
        student.setUser(user);


        studentService.assignGroup(group.getId(), student.getId());

        assertEquals(1, studentService.findStudentsByGroupName(group.getName()).size());
    }

    @Test
    void findStudentsByGroupName_shouldThrowGroupNotFoundException_whenGroupDoesNotExist() {
        assertThrows(GroupNotFoundException.class, () -> studentService.findStudentsByGroupName("Some name"));
    }


}
