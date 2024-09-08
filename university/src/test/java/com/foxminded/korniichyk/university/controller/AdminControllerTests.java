package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.annotation.WithMockCustomUser;
import com.foxminded.korniichyk.university.controller.admin.AdminController;
import com.foxminded.korniichyk.university.dto.display.*;
import com.foxminded.korniichyk.university.dto.registration.AdminRegistrationDto;
import com.foxminded.korniichyk.university.dto.registration.StudentRegistrationDto;
import com.foxminded.korniichyk.university.dto.registration.TeacherRegistrationDto;
import com.foxminded.korniichyk.university.dto.registration.UserRegistrationDto;
import com.foxminded.korniichyk.university.dto.update.AdminUpdateDto;
import com.foxminded.korniichyk.university.dto.update.StudentUpdateDto;
import com.foxminded.korniichyk.university.dto.update.TeacherUpdateDto;
import com.foxminded.korniichyk.university.dto.update.UserUpdateDto;
import com.foxminded.korniichyk.university.mapper.update.AdminUpdateMapper;
import com.foxminded.korniichyk.university.mapper.update.StudentUpdateMapper;
import com.foxminded.korniichyk.university.mapper.update.TeacherUpdateMapper;
import com.foxminded.korniichyk.university.mapper.update.UserUpdateMapper;
import com.foxminded.korniichyk.university.security.CustomUserDetailsService;
import com.foxminded.korniichyk.university.security.SecurityConfig;
import com.foxminded.korniichyk.university.service.contract.*;
import com.foxminded.korniichyk.university.service.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private DisciplineService disciplineService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private LessonTypeService lessonTypeService;

    @MockBean
    private SpecialityService specialityService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private StudentUpdateMapper studentUpdateMapper;

    @MockBean
    private UserUpdateMapper userUpdateMapper;

    @MockBean
    private TeacherUpdateMapper teacherUpdateMapper;

    @MockBean
    private AdminUpdateMapper adminUpdateMapper;

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    void admins_shouldReturnCorrectViewWithAttributes() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Admin");
        userDto.setLastName("User");
        userDto.setEmail("admin@gmail.com");

        AdminDto adminDto = new AdminDto();
        adminDto.setUser(userDto);

        Page<AdminDto> page = new PageImpl<>(Collections.singletonList(adminDto), PageRequest.of(0, 7), 1);

        when(adminService.findPage(anyInt(), anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/administrators/admins")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/administrators"))
                .andExpect(model().attributeExists("administrators"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("administrators", page.getContent()))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void admins_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/admins"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void disciplines_shouldReturnCorrectViewWithAttributes() throws Exception {
        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("");
        disciplineDto.setDescription("");

        Page<DisciplineDto> page = new PageImpl<>(Collections.singletonList(disciplineDto), PageRequest.of(0, 7), 1);

        when(disciplineService.findPage(anyInt(), anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/administrators/disciplines")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/disciplines"))
                .andExpect(model().attributeExists("disciplines"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("disciplines", page.getContent()))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void disciplines_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/disciplines"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void specialities_shouldReturnCorrectViewWithAttributes() throws Exception {
        SpecialityDto specialityDto = new SpecialityDto();
        specialityDto.setId(1L);
        specialityDto.setName("");
        specialityDto.setDescription("");

        Page<SpecialityDto> page = new PageImpl<>(Collections.singletonList(specialityDto), PageRequest.of(0, 7), 1);

        when(specialityService.findPage(anyInt(), anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/administrators/specialities")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/specialities"))
                .andExpect(model().attributeExists("specialities"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("specialities", page.getContent()))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void specialities_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/specialities"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void groups_shouldReturnCorrectViewWithAttributes() throws Exception {
        SpecialityDto specialityDto = new SpecialityDto();
        specialityDto.setName("");

        GroupDto groupDto = new GroupDto();
        groupDto.setId(1L);
        groupDto.setName("Group A");
        groupDto.setStudents(Set.of(new StudentDto()));
        groupDto.setSpeciality(specialityDto);

        Page<GroupDto> page = new PageImpl<>(Collections.singletonList(groupDto), PageRequest.of(0, 7), 1);

        when(groupService.findPage(anyInt(), anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/administrators/groups")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/groups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("groups", page.getContent()))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void groups_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/groups"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void lessonTypes_shouldReturnCorrectViewWithAttributes() throws Exception {
        LessonTypeDto lessonTypeDto = new LessonTypeDto();
        lessonTypeDto.setId(1L);
        lessonTypeDto.setName("Lecture");

        Page<LessonTypeDto> page = new PageImpl<>(Collections.singletonList(lessonTypeDto), PageRequest.of(0, 7), 1);

        when(lessonTypeService.findPage(anyInt(), anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/administrators/lesson-types")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/lesson-types"))
                .andExpect(model().attributeExists("lessonTypes"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("lessonTypes", page.getContent()))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void lessonTypes_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/lesson-types"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void students_shouldReturnCorrectViewWithAttributes() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Admin");
        userDto.setLastName("User");
        userDto.setEmail("admin@gmail.com");


        StudentDto studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setUser(userDto);

        Page<StudentDto> page = new PageImpl<>(Collections.singletonList(studentDto), PageRequest.of(0, 7), 1);

        when(studentService.findPage(anyInt(), anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/administrators/students")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("students", page.getContent()))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void students_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/students"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void teachers_shouldReturnCorrectViewWithAttributes() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Admin");
        userDto.setLastName("User");
        userDto.setEmail("admin@gmail.com");

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setUser(userDto);
        teacherDto.setDisciplines(Set.of(new DisciplineDto()));

        Page<TeacherDto> page = new PageImpl<>(Collections.singletonList(teacherDto), PageRequest.of(0, 7), 1);

        when(teacherService.findPage(anyInt(), anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/administrators/teachers")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/teachers"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("teachers", page.getContent()))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void teachers_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/teachers"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void lessons_shouldReturnCorrectViewWithAttributes() throws Exception {
        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setName("");

        LessonTypeDto lessonTypeDto = new LessonTypeDto();
        lessonTypeDto.setName("");

        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(1L);
        lessonDto.setDiscipline(disciplineDto);
        lessonDto.setLessonType(lessonTypeDto);
        lessonDto.setClassroomNumber(100);
        lessonDto.setDate(LocalDate.now());

        Page<LessonDto> page = new PageImpl<>(Collections.singletonList(lessonDto), PageRequest.of(0, 7), 1);

        when(lessonService.findPage(anyInt(), anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/administrators/lessons")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/lessons"))
                .andExpect(model().attributeExists("lessons"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("lessons", page.getContent()))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void lessons_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/lessons"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void createStudentPage_shouldReturnCorrectViewWithAttributes() throws Exception {
        List<GroupDto> groups = List.of(new GroupDto(), new GroupDto());
        when(groupService.findAll()).thenReturn(groups);

        mockMvc.perform(get("/administrators/students/create-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-student"))
                .andExpect(model().attributeExists("studentRegistrationDto"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", groups));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void createStudentPage_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/students/create-page"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void createTeacherPage_shouldReturnCorrectViewWithAttributes() throws Exception {
        List<DisciplineDto> disciplines = List.of(new DisciplineDto(), new DisciplineDto());
        when(disciplineService.findAll()).thenReturn(disciplines);

        mockMvc.perform(get("/administrators/teachers/create-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-teacher"))
                .andExpect(model().attributeExists("teacherRegistrationDto"))
                .andExpect(model().attributeExists("disciplines"))
                .andExpect(model().attribute("disciplines", disciplines));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void createTeacherPage_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/teachers/create-page"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN"})
    void createAdminPage_shouldReturnCorrectViewWithAttributes() throws Exception {


        mockMvc.perform(get("/administrators/admins/create-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-admin"))
                .andExpect(model().attributeExists("adminRegistrationDto"));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"STUDENT", "TEACHER"})
    void createAdminPage_shouldReturnForbidden_withNonAdminRoles() throws Exception {
        mockMvc.perform(get("/administrators/admins/create-page"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createStudent_shouldRedirect_whenValidationSuccess() throws Exception {

        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("email@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        studentRegistrationDto.setUser(userRegistrationDto);
        studentRegistrationDto.setGroupId(1L);

        ResultActions result = mockMvc.perform(post("/administrators/students/create-page")
                .with(csrf())
                .flashAttr("studentRegistrationDto", studentRegistrationDto));


        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/students"));


        verify(studentService, times(1)).registerStudent(studentRegistrationDto);

    }


    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createStudent_shouldReturnFormOnValidationErrors_whenPasswordsDontMatch() throws Exception {
        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("email@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("anotherPassword");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        studentRegistrationDto.setUser(userRegistrationDto);
        studentRegistrationDto.setGroupId(1L);

        GroupDto groupDto = new GroupDto();
        groupDto.setId(1L);
        groupDto.setName("Name");


        List<GroupDto> groups = Collections.singletonList(groupDto);
        when(groupService.findAll()).thenReturn(groups);

        ResultActions result = mockMvc.perform(post("/administrators/students/create-page")
                .with(csrf())
                .flashAttr("studentRegistrationDto", studentRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-student"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "user.confirmPassword"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", groups));

        verify(studentService, never()).registerStudent(any());
    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createStudent_shouldReturnFormOnValidationErrors_whenAllFieldsNotBlanked() throws Exception {
        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("");
        userRegistrationDto.setLastName("");
        userRegistrationDto.setEmail("badEmail");
        userRegistrationDto.setPassword("");
        userRegistrationDto.setConfirmPassword("");
        userRegistrationDto.setPhoneNumber("");

        studentRegistrationDto.setUser(userRegistrationDto);

        GroupDto groupDto = new GroupDto();
        groupDto.setId(1L);
        groupDto.setName("Name");


        List<GroupDto> groups = Collections.singletonList(groupDto);
        when(groupService.findAll()).thenReturn(groups);

        ResultActions result = mockMvc.perform(post("/administrators/students/create-page")
                .with(csrf())
                .flashAttr("studentRegistrationDto", studentRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-student"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "user.password"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "user.firstName"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "user.lastName"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "user.email"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "user.phoneNumber"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "user.dateOfBirth"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "groupId"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", groups));

        verify(studentService, never()).registerStudent(any());
    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createStudent_shouldReturnFormOnValidationErrors_whenBlankedEmailAlreadyExists() throws Exception {
        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("existingEmail@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        studentRegistrationDto.setUser(userRegistrationDto);
        studentRegistrationDto.setGroupId(1L);

        GroupDto groupDto = new GroupDto();
        groupDto.setId(1L);
        groupDto.setName("Name");


        List<GroupDto> groups = Collections.singletonList(groupDto);
        when(groupService.findAll()).thenReturn(groups);


        doThrow(new EmailAlreadyExistsException("Email already exists")).when(studentService).registerStudent(any());

        ResultActions result = mockMvc.perform(post("/administrators/students/create-page")
                .with(csrf())
                .flashAttr("studentRegistrationDto", studentRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-student"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "user.email"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", groups));

        verify(studentService, times(1)).registerStudent(studentRegistrationDto);

    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createStudent_shouldReturnFormOnValidationErrors_whenBlankedPhoneNumberAlreadyExists() throws Exception {
        StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("email@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        studentRegistrationDto.setUser(userRegistrationDto);
        studentRegistrationDto.setGroupId(1L);

        GroupDto groupDto = new GroupDto();
        groupDto.setId(1L);
        groupDto.setName("Name");


        List<GroupDto> groups = Collections.singletonList(groupDto);
        when(groupService.findAll()).thenReturn(groups);


        doThrow(new PhoneNumberAlreadyExistsException("Phone number already exists")).when(studentService).registerStudent(any());

        ResultActions result = mockMvc.perform(post("/administrators/students/create-page")
                .with(csrf())
                .flashAttr("studentRegistrationDto", studentRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-student"))
                .andExpect(model().attributeHasFieldErrors("studentRegistrationDto", "user.phoneNumber"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", groups));

        verify(studentService, times(1)).registerStudent(studentRegistrationDto);

    }


    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createTeacher_shouldRedirect_whenValidationSuccess() throws Exception {

        TeacherRegistrationDto teacherRegistrationDto = new TeacherRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("email@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        teacherRegistrationDto.setUser(userRegistrationDto);
        teacherRegistrationDto.setDisciplineIds(Set.of(1L, 2L));

        ResultActions result = mockMvc.perform(post("/administrators/teachers/create-page")
                .with(csrf())
                .flashAttr("teacherRegistrationDto", teacherRegistrationDto));


        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/teachers"));


        verify(teacherService, times(1)).registerTeacher(teacherRegistrationDto);

    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createTeacher_shouldReturnFormOnValidationErrors_whenPasswordsDontMatch() throws Exception {
        TeacherRegistrationDto teacherRegistrationDto = new TeacherRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("email@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("anotherPassword");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        teacherRegistrationDto.setUser(userRegistrationDto);
        teacherRegistrationDto.setDisciplineIds(Set.of(1L, 2L));

        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("Name");


        List<DisciplineDto> disciplines = Collections.singletonList(disciplineDto);
        when(disciplineService.findAll()).thenReturn(disciplines);

        ResultActions result = mockMvc.perform(post("/administrators/teachers/create-page")
                .with(csrf())
                .flashAttr("teacherRegistrationDto", teacherRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-teacher"))
                .andExpect(model().attributeHasFieldErrors("teacherRegistrationDto", "user.confirmPassword"))
                .andExpect(model().attributeExists("disciplines"))
                .andExpect(model().attribute("disciplines", disciplines));

        verify(studentService, never()).registerStudent(any());
    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createTeacher_shouldReturnFormOnValidationErrors_whenAllFieldsNotBlanked() throws Exception {
        TeacherRegistrationDto teacherRegistrationDto = new TeacherRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("");
        userRegistrationDto.setLastName("");
        userRegistrationDto.setEmail("a@");
        userRegistrationDto.setPassword("");
        userRegistrationDto.setConfirmPassword("");
        userRegistrationDto.setPhoneNumber("");

        teacherRegistrationDto.setUser(userRegistrationDto);

        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("Name");


        List<DisciplineDto> disciplines = Collections.singletonList(disciplineDto);
        when(disciplineService.findAll()).thenReturn(disciplines);

        ResultActions result = mockMvc.perform(post("/administrators/teachers/create-page")
                .with(csrf())
                .flashAttr("teacherRegistrationDto", teacherRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-teacher"))
                .andExpect(model().attributeHasFieldErrors("teacherRegistrationDto", "user.password"))
                .andExpect(model().attributeHasFieldErrors("teacherRegistrationDto", "user.firstName"))
                .andExpect(model().attributeHasFieldErrors("teacherRegistrationDto", "user.lastName"))
                .andExpect(model().attributeHasFieldErrors("teacherRegistrationDto", "user.email"))
                .andExpect(model().attributeHasFieldErrors("teacherRegistrationDto", "user.phoneNumber"))
                .andExpect(model().attributeHasFieldErrors("teacherRegistrationDto", "user.dateOfBirth"))
                .andExpect(model().attributeExists("disciplines"))
                .andExpect(model().attribute("disciplines", disciplines));

        verify(studentService, never()).registerStudent(any());
    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createTeacher_shouldReturnFormOnValidationErrors_whenBlankedEmailAlreadyExists() throws Exception {
        TeacherRegistrationDto teacherRegistrationDto = new TeacherRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("existingEmail@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0998790987");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        teacherRegistrationDto.setUser(userRegistrationDto);

        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("Name");

        List<DisciplineDto> disciplines = Collections.singletonList(disciplineDto);
        when(disciplineService.findAll()).thenReturn(disciplines);


        doThrow(new EmailAlreadyExistsException("Email already exists")).when(teacherService).registerTeacher(any());

        ResultActions result = mockMvc.perform(post("/administrators/teachers/create-page")
                .with(csrf())
                .flashAttr("teacherRegistrationDto", teacherRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-teacher"))
                .andExpect(model().attributeHasFieldErrors("teacherRegistrationDto", "user.email"))
                .andExpect(model().attributeExists("disciplines"))
                .andExpect(model().attribute("disciplines", disciplines));

        verify(teacherService, times(1)).registerTeacher(teacherRegistrationDto);

    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createTeacher_shouldReturnFormOnValidationErrors_whenBlankedPhoneNumberAlreadyExists() throws Exception {
        TeacherRegistrationDto teacherRegistrationDto = new TeacherRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("existingEmail@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0998790987");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        teacherRegistrationDto.setUser(userRegistrationDto);

        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("Name");

        List<DisciplineDto> disciplines = Collections.singletonList(disciplineDto);
        when(disciplineService.findAll()).thenReturn(disciplines);


        doThrow(new PhoneNumberAlreadyExistsException("")).when(teacherService).registerTeacher(any());

        ResultActions result = mockMvc.perform(post("/administrators/teachers/create-page")
                .with(csrf())
                .flashAttr("teacherRegistrationDto", teacherRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-teacher"))
                .andExpect(model().attributeHasFieldErrors("teacherRegistrationDto", "user.phoneNumber"))
                .andExpect(model().attributeExists("disciplines"))
                .andExpect(model().attribute("disciplines", disciplines));

        verify(teacherService, times(1)).registerTeacher(teacherRegistrationDto);

    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createAdmin_shouldRedirect_whenValidationSuccess() throws Exception {

        AdminRegistrationDto adminRegistrationDto = new AdminRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("email@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        adminRegistrationDto.setUser(userRegistrationDto);

        ResultActions result = mockMvc.perform(post("/administrators/admins/create-page")
                .with(csrf())
                .flashAttr("adminRegistrationDto", adminRegistrationDto));


        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/admins"));


        verify(adminService, times(1)).registerAdmin(adminRegistrationDto);

    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createAdmin_shouldReturnFormOnValidationErrors_whenPasswordsDontMatch() throws Exception {
        AdminRegistrationDto adminRegistrationDto = new AdminRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("email@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("anotherPassword");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        adminRegistrationDto.setUser(userRegistrationDto);


        ResultActions result = mockMvc.perform(post("/administrators/admins/create-page")
                .with(csrf())
                .flashAttr("adminRegistrationDto", adminRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-admin"))
                .andExpect(model().attributeHasFieldErrors("adminRegistrationDto", "user.confirmPassword"));

        verify(adminService, never()).registerAdmin(any());
    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createAdmin_shouldReturnFormOnValidationErrors_whenAllFieldsNotBlanked() throws Exception {
        AdminRegistrationDto adminRegistrationDto = new AdminRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("");
        userRegistrationDto.setLastName("");
        userRegistrationDto.setEmail("badEmail");
        userRegistrationDto.setPassword("");
        userRegistrationDto.setConfirmPassword("");
        userRegistrationDto.setPhoneNumber("");

        adminRegistrationDto.setUser(userRegistrationDto);


        ResultActions result = mockMvc.perform(post("/administrators/admins/create-page")
                .with(csrf())
                .flashAttr("adminRegistrationDto", adminRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-admin"))
                .andExpect(model().attributeHasFieldErrors("adminRegistrationDto", "user.password"))
                .andExpect(model().attributeHasFieldErrors("adminRegistrationDto", "user.firstName"))
                .andExpect(model().attributeHasFieldErrors("adminRegistrationDto", "user.lastName"))
                .andExpect(model().attributeHasFieldErrors("adminRegistrationDto", "user.email"))
                .andExpect(model().attributeHasFieldErrors("adminRegistrationDto", "user.phoneNumber"))
                .andExpect(model().attributeHasFieldErrors("adminRegistrationDto", "user.dateOfBirth"));


        verify(adminService, never()).registerAdmin(any());
    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createAdmin_shouldReturnFormOnValidationErrors_whenBlankedEmailAlreadyExists() throws Exception {
        AdminRegistrationDto adminRegistrationDto = new AdminRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("existingEmail@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        adminRegistrationDto.setUser(userRegistrationDto);


        doThrow(new EmailAlreadyExistsException("Email already exists")).when(adminService).registerAdmin(any());

        ResultActions result = mockMvc.perform(post("/administrators/admins/create-page")
                .with(csrf())
                .flashAttr("adminRegistrationDto", adminRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-admin"))
                .andExpect(model().attributeHasFieldErrors("adminRegistrationDto", "user.email"));


        verify(adminService, times(1)).registerAdmin(adminRegistrationDto);

    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", roles = "ROLE_ADMIN")
    void createAdmin_shouldReturnFormOnValidationErrors_whenBlankedPhoneNumberAlreadyExists() throws Exception {
        AdminRegistrationDto adminRegistrationDto = new AdminRegistrationDto();

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Name");
        userRegistrationDto.setLastName("LastName");
        userRegistrationDto.setEmail("email@gmail.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setPhoneNumber("0999999999");
        userRegistrationDto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        adminRegistrationDto.setUser(userRegistrationDto);


        doThrow(new PhoneNumberAlreadyExistsException("")).when(adminService).registerAdmin(any());

        ResultActions result = mockMvc.perform(post("/administrators/admins/create-page")
                .with(csrf())
                .flashAttr("adminRegistrationDto", adminRegistrationDto));

        result.andExpect(status().isOk())
                .andExpect(view().name("admin/create/create-admin"))
                .andExpect(model().attributeHasFieldErrors("adminRegistrationDto", "user.phoneNumber"));


        verify(adminService, times(1)).registerAdmin(adminRegistrationDto);

    }


    //DELETE FEATURE TESTS
    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void deleteAdmin_shouldRedirectWithSuccessMessage_whenAdminDeletedSuccessfully() throws Exception {
        Long adminId = 1L;

        mockMvc.perform(post("/administrators/admins/delete/{id}", adminId)
                        .param("_method", "delete")
                        .with(csrf()))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/admins"))
                .andExpect(flash().attribute("successMessage", "Admin deleted successfully"));

        verify(adminService, times(1)).deleteById(adminId);
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void deleteAdmin_AdminNotFound_shouldAddErrorMessage() throws Exception {
        // Arrange
        Long adminId = 1L;
        doThrow(new AdminNotFoundException("Admin not found")).when(adminService).deleteById(adminId);

        // Act & Assert
        mockMvc.perform(post("/administrators/admins/delete/{id}", adminId)
                        .param("_method", "delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/admins"))
                .andExpect(flash().attribute("errorMessage", "Admin not found"));

        // Verify that the service method was called
        verify(adminService).deleteById(adminId);
    }


    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void deleteStudent_shouldRedirectWithSuccessMessage_whenStudentDeletedSuccessfully() throws Exception {
        Long studentId = 1L;

        mockMvc.perform(post("/administrators/students/delete/{id}", studentId)
                        .param("_method", "delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/students"))
                .andExpect(flash().attribute("successMessage", "Student deleted successfully"));

        verify(studentService, times(1)).deleteById(studentId);
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void deleteStudent_StudentNotFound_shouldAddErrorMessage() throws Exception {
        Long studentId = 1L;
        doThrow(new StudentNotFoundException("Student not found")).when(studentService).deleteById(studentId);

        mockMvc.perform(post("/administrators/students/delete/{id}", studentId)
                        .param("_method", "delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/students"))
                .andExpect(flash().attribute("errorMessage", "Student not found"));

        verify(studentService).deleteById(studentId);
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void deleteTeacher_shouldRedirectWithSuccessMessage_whenTeacherDeletedSuccessfully() throws Exception {
        // Arrange
        Long teacherId = 1L;

        // Act
        mockMvc.perform(post("/administrators/teachers/delete/{id}", teacherId)
                        .param("_method", "delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/teachers"))
                .andExpect(flash().attribute("successMessage", "Teacher deleted successfully"));

        // Assert
        verify(teacherService, times(1)).deleteById(teacherId);
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void deleteTeacher_TeacherNotFound_ShouldAddErrorMessage() throws Exception {
        Long teacherId = 1L;
        doThrow(new TeacherNotFoundException("Teacher not found")).when(teacherService).deleteById(teacherId);

        mockMvc.perform(post("/administrators/teachers/delete/{id}", teacherId)
                        .param("_method", "delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/teachers"))
                .andExpect(flash().attribute("errorMessage", "Teacher not found"));

        verify(teacherService).deleteById(teacherId);
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void showTeacherEditPage_success() throws Exception {

        TeacherUpdateDto teacherUpdateDto = new TeacherUpdateDto();
        when(teacherService.getTeacherUpdateDto(anyLong())).thenReturn(teacherUpdateDto);
        when(disciplineService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/administrators/teachers/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/edit/edit-teacher"))
                .andExpect(model().attributeExists("teacherUpdateDto"))
                .andExpect(model().attributeExists("disciplines"));
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void showTeacherEditPage_errorMessageShouldBePresented_whenTeacherNotFound() throws Exception {
        when(teacherService.getTeacherUpdateDto(anyLong())).thenThrow(new TeacherNotFoundException("Teacher not found"));

        mockMvc.perform(get("/administrators/teachers/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/teachers"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void editTeacher_Success() throws Exception {
        when(disciplineService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/administrators/teachers/edit")
                        .flashAttr("teacherUpdateDto", new TeacherUpdateDto())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/teachers"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void editTeacher_ValidationErrors() throws Exception {
        // Given
        UserUpdateDto invalidUserUpdateDto = new UserUpdateDto();

        invalidUserUpdateDto.setEmail("invalid-email");


        TeacherUpdateDto invalidTeacherUpdateDto = new TeacherUpdateDto();
        invalidTeacherUpdateDto.setUser(invalidUserUpdateDto);

        // Mock service methods
        when(disciplineService.findAll()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(post("/administrators/teachers/edit")
                        .flashAttr("teacherUpdateDto", invalidTeacherUpdateDto)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/edit/edit-teacher"))
                .andExpect(model().attributeExists("teacherUpdateDto"))
                .andExpect(model().attributeExists("disciplines"))
                .andExpect(model().attributeHasFieldErrors("teacherUpdateDto", "user.firstName"))
                .andExpect(model().attributeHasFieldErrors("teacherUpdateDto", "user.email"))
                .andExpect(model().attributeHasFieldErrors("teacherUpdateDto", "user.lastName"))
                .andExpect(model().attributeHasFieldErrors("teacherUpdateDto", "user.phoneNumber"))
                .andExpect(model().attributeHasFieldErrors("teacherUpdateDto", "user.dateOfBirth"));
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void showAdminEditPage_success() throws Exception {

        AdminUpdateDto adminUpdateDto = new AdminUpdateDto();
        when(adminService.getAdminUpdateDto(anyLong())).thenReturn(adminUpdateDto);
        when(disciplineService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/administrators/admins/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/edit/edit-admin"))
                .andExpect(model().attributeExists("adminUpdateDto"));
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void showAdminEditPage_errorMessageShouldBePresented_whenAdminNotFound() throws Exception {
        when(adminService.getAdminUpdateDto(anyLong())).thenThrow(new AdminNotFoundException("Admin not found"));

        mockMvc.perform(get("/administrators/admins/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/admins"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void editAdmin_Success() throws Exception {

        mockMvc.perform(post("/administrators/admins/edit")
                        .flashAttr("adminUpdateDto", new AdminUpdateDto())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/admins"))
                .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void editAdmin_ValidationErrors() throws Exception {
        UserUpdateDto invalidUserUpdateDto = new UserUpdateDto();

        invalidUserUpdateDto.setEmail("invalid-email");


        AdminUpdateDto invalidAdminUpdateDto = new AdminUpdateDto();
        invalidAdminUpdateDto.setUser(invalidUserUpdateDto);

        when(disciplineService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/administrators/admins/edit")
                        .flashAttr("adminUpdateDto", invalidAdminUpdateDto)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/edit/edit-admin"))
                .andExpect(model().attributeExists("adminUpdateDto"))
                .andExpect(model().attributeHasFieldErrors("adminUpdateDto", "user.firstName"))
                .andExpect(model().attributeHasFieldErrors("adminUpdateDto", "user.email"))
                .andExpect(model().attributeHasFieldErrors("adminUpdateDto", "user.lastName"))
                .andExpect(model().attributeHasFieldErrors("adminUpdateDto", "user.phoneNumber"))
                .andExpect(model().attributeHasFieldErrors("adminUpdateDto", "user.dateOfBirth"));
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void showStudentEditPage_success() throws Exception {

        StudentUpdateDto studentUpdateDto = new StudentUpdateDto();
        when(studentService.getStudentUpdateDto(anyLong())).thenReturn(studentUpdateDto);
        when(groupService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/administrators/students/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/edit/edit-student"))
                .andExpect(model().attributeExists("studentUpdateDto"));
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void showStudentEditPage_errorMessageShouldBePresented_whenStudentNotFound() throws Exception {
        when(studentService.getStudentUpdateDto(anyLong())).thenThrow(new StudentNotFoundException("Student not found"));
        when(groupService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/administrators/students/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/administrators/students"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    @Test
    @WithMockCustomUser(email = "admin@gmail.com", roles = "ROLE_ADMIN")
    void editStudent_Success() throws Exception {
        StudentUpdateDto studentUpdateDto = new StudentUpdateDto();
        studentUpdateDto.setId(1L);
        studentUpdateDto.setGroupId(2L);

        doNothing().when(studentService).save(any());
        doNothing().when(studentService).assignGroup(anyLong(), anyLong());

        // Perform the request
        mockMvc.perform(post("/administrators/students/edit")
                        .flashAttr("studentUpdateDto", studentUpdateDto)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())  // Expect redirection
                .andExpect(redirectedUrl("/administrators/students"))  // Expect the correct URL
                .andExpect(flash().attributeExists("successMessage"));  // Check for success message
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void editStudent_ValidationErrors() throws Exception {
        UserUpdateDto invalidUserUpdateDto = new UserUpdateDto();

        invalidUserUpdateDto.setEmail("invalid-email");


        StudentUpdateDto invalidStudentUpdateDto = new StudentUpdateDto();
        invalidStudentUpdateDto.setUser(invalidUserUpdateDto);

        when(groupService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/administrators/students/edit")
                        .flashAttr("studentUpdateDto", invalidStudentUpdateDto)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/edit/edit-student"))
                .andExpect(model().attributeExists("studentUpdateDto"))
                .andExpect(model().attributeHasFieldErrors("studentUpdateDto", "user.firstName"))
                .andExpect(model().attributeHasFieldErrors("studentUpdateDto", "user.email"))
                .andExpect(model().attributeHasFieldErrors("studentUpdateDto", "user.lastName"))
                .andExpect(model().attributeHasFieldErrors("studentUpdateDto", "user.phoneNumber"))
                .andExpect(model().attributeHasFieldErrors("studentUpdateDto", "user.dateOfBirth"))
                .andExpect(model().attributeHasFieldErrors("studentUpdateDto", "groupId"));
    }

}
