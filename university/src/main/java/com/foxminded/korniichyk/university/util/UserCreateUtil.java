package com.foxminded.korniichyk.university.util;

import com.foxminded.korniichyk.university.dao.GroupDao;
import com.foxminded.korniichyk.university.dao.TeacherDao;
import com.foxminded.korniichyk.university.dao.UserDao;
import com.foxminded.korniichyk.university.model.*;
import com.foxminded.korniichyk.university.service.contract.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class UserCreateUtil implements ApplicationRunner {


    private final UserService userService;

    private final AdminService adminService;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final UserDao userDao;

    private final GroupDao groupDao;

    private final StudentService studentService;

    private final TeacherService teacherService;


    @Autowired
    public UserCreateUtil(UserService userService,
                          AdminService adminService,
                          PasswordEncoder passwordEncoder,
                          RoleService roleService,
                          UserDao userDao,
                          GroupDao groupDao,
                          StudentService studentService,
                          TeacherService teacherService
    ) {
        this.userService = userService;
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }


    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAdmin();
        createStudent();
        createTeacher();
    }

    private void createStudent() {
        if(userDao.findByEmail("student@gmail.com") == null) {
            User user = new User();
            user.setFirstName("Pavel");
            user.setLastName("Honcharevskiy");
            user.setPhoneNumber("0508843133");
            user.setDateOfBirth(LocalDate.of(2000, 05, 25));
            user.setEmail("student@gmail.com");

            String encodedPassword = passwordEncoder.encode("student");
            user.setPassword(encodedPassword);

            Role studentRole = roleService.findByName("ROLE_STUDENT");
            if (studentRole == null) {
                throw new IllegalStateException("Student role not found in the database");
            }
            user.setRoles(Set.of(studentRole));

            userService.save(user);

            Student student = new Student();
            student.setUser(user);
            student.setGroup(groupDao.findAll().get(0));
            studentService.save(student);
        }
    }

    public void createAdmin() {
        if (userDao.findByEmail("admin@gmail.com") == null) {
            User user = new User();
            user.setFirstName("Illia");
            user.setLastName("Korniichyk");
            user.setPhoneNumber("0509843822");
            user.setDateOfBirth(LocalDate.of(2003, 10, 25));
            user.setEmail("admin@gmail.com");

            String encodedPassword = passwordEncoder.encode("admin");
            user.setPassword(encodedPassword);

            Role adminRole = roleService.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                throw new IllegalStateException("Admin role not found in the database");
            }
            user.setRoles(Set.of(adminRole));

            userService.save(user);

            Admin admin = new Admin();
            admin.setUser(user);

            adminService.save(admin);


        }
    }

    public void createTeacher() {
        if (userDao.findByEmail("teacher@gmail.com") == null) {
            User user = new User();
            user.setFirstName("Oleg");
            user.setLastName("Shtifzon");
            user.setPhoneNumber("0950943856");
            user.setDateOfBirth(LocalDate.of(1970, 7, 11));
            user.setEmail("teacher@gmail.com");

            String encodedPassword = passwordEncoder.encode("teacher");
            user.setPassword(encodedPassword);

            Role teacherRole = roleService.findByName("ROLE_TEACHER");
            if (teacherRole == null) {
                throw new IllegalStateException("Teacher role not found in the database");
            }
            user.setRoles(Set.of(teacherRole));

            userService.save(user);

            Teacher teacher = new Teacher();
            teacher.setUser(user);

            teacherService.save(teacher);


        }
    }
}
