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
import com.foxminded.korniichyk.university.service.exception.GroupAlreadyAssignedException;
import com.foxminded.korniichyk.university.service.exception.GroupNotFoundException;
import com.foxminded.korniichyk.university.service.exception.StudentNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        Speciality speciality = new Speciality();
        speciality.setCode(121);
        speciality.setName("Software engineering");
        specialityService.save(speciality);

        Group group = new Group();
        group.setName("Group 1");
        groupService.save(group);


        Student student = new Student();
        User user = new User();
        user.setFirstName("Max");
        user.setLastName("White");
        user.setEmail("max11@gmail.com");
        user.setPhoneNumber("122456789");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userService.save(user);
        student.setUser(user);
        studentService.save(student);

        studentService.assignGroup(group.getId(), student.getId());

        StudentDto result = studentService.findById(student.getId());
        assertEquals(result.getGroupName(), group.getName());
    }

    @Test
    void assignGroup_shouldThrowGroupNotFoundException_whenGroupDoesNotExist() {
        Student student = new Student();
        User user = new User();
        user.setFirstName("Max");
        user.setLastName("White");
        user.setEmail("max11@gmail.com");
        user.setPhoneNumber("122456789");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userService.save(user);
        student.setUser(user);
        studentService.save(student);

        assertThrows(GroupNotFoundException.class, () -> studentService.assignGroup(1L, student.getId()));

    }

    @Test
    void assignGroup_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {
        Speciality speciality = new Speciality();
        speciality.setCode(121);
        speciality.setName("Software engineering");
        specialityService.save(speciality);

        Group group = new Group();
        group.setName("Group 1");
        groupService.save(group);

        assertThrows(StudentNotFoundException.class, () -> studentService.assignGroup(group.getId(), 1L));

    }

    @Test
    void assignGroup_shouldThrowGroupAlreadyAssignedException_whenGroupAlreadyAssigned() {
        Speciality speciality = new Speciality();
        speciality.setCode(121);
        speciality.setName("Software engineering");
        specialityService.save(speciality);

        Group group = new Group();
        group.setName("Group 1");
        groupService.save(group);


        Student student = new Student();
        User user = new User();
        user.setFirstName("Max");
        user.setLastName("White");
        user.setEmail("max11@gmail.com");
        user.setPhoneNumber("122456789");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userService.save(user);
        student.setUser(user);
        studentService.save(student);

        studentService.assignGroup(group.getId(), student.getId());
        assertThrows(GroupAlreadyAssignedException.class, () -> studentService.assignGroup(group.getId(), student.getId()));
    }

    @Test
    void findStudentsByGroupId_shouldReturnStudents_whenGroupHasStudents() {
        Speciality speciality = new Speciality();
        speciality.setCode(121);
        speciality.setName("Software engineering");
        specialityService.save(speciality);

        Group group = new Group();
        group.setName("Group 1");
        groupService.save(group);


        Student student = new Student();
        User user = new User();
        user.setFirstName("Max");
        user.setLastName("White");
        user.setEmail("max11@gmail.com");
        user.setPhoneNumber("122456789");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userService.save(user);
        student.setUser(user);
        studentService.save(student);

        studentService.assignGroup(group.getId(), student.getId());

        assertEquals(1, studentService.findStudentsPageByGroupId(group.getId(),0,10).getContent().size());

    }

    @Test
    void findStudentsByGroupId_shouldThrowGroupNotFoundException_whenGroupDoesNotExist() {

        assertThrows(GroupNotFoundException.class, () -> studentService.findStudentsPageByGroupId(1L,1,10));
    }

    @Test
    void findStudentsByGroupName_shouldReturnStudents_whenGroupHasStudents() {
        Speciality speciality = new Speciality();
        speciality.setCode(121);
        speciality.setName("Software engineering");
        specialityService.save(speciality);

        Group group = new Group();
        group.setName("Group 1");
        groupService.save(group);


        Student student = new Student();
        User user = new User();
        user.setFirstName("Max");
        user.setLastName("White");
        user.setEmail("max11@gmail.com");
        user.setPhoneNumber("122456789");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userService.save(user);
        student.setUser(user);
        studentService.save(student);

        studentService.assignGroup(group.getId(), student.getId());

        assertEquals(1, studentService.findStudentsByGroupName(group.getName()).size());
    }

    @Test
    void findStudentsByGroupName_shouldThrowGroupNotFoundException_whenGroupDoesNotExist() {
        assertThrows(GroupNotFoundException.class, () -> studentService.findStudentsByGroupName("Some name"));
    }


}
