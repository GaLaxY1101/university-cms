package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.annotation.WithMockCustomUser;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.dto.display.UserDto;
import com.foxminded.korniichyk.university.mapper.display.GroupMapper;
import com.foxminded.korniichyk.university.model.Group;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.model.Speciality;
import com.foxminded.korniichyk.university.model.Student;
import com.foxminded.korniichyk.university.security.CustomUserDetailsService;
import com.foxminded.korniichyk.university.security.SecurityConfig;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@WebMvcTest(StudentController.class)
public class StudentsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean private GroupService groupService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private GroupMapper groupMapper;

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN","TEACHER"})
        void students_shouldReturnCorrectViewWithAttributes() throws Exception {
        GroupDto group = new GroupDto();
        group.setName("Group 1");

        StudentDto student = new StudentDto();
        UserDto user = new UserDto();
        user.setId(1L);
        user.setFirstName("Joh");
        user.setLastName("Smith");
        student.setGroupName("group 1");
        user.setEmail("");
        student.setUser(user);
        Page<StudentDto> page = new PageImpl<>(Collections.singletonList(student), PageRequest.of(0, 7), 1);

        when(studentService.findPage(anyInt(),anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/students/")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("students", page))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }

    @Test
    @WithMockUser(username = "student@gmail.com", roles = {"STUDENT"})
    void groups_shouldReturnForbiddenForStudentRole() throws Exception {

        // Mock the StudentService to return a Page object
        Page<StudentDto> emptyPage = Page.empty(); // Or any appropriate mock page
        when(studentService.findPage(anyInt(), anyInt())).thenReturn(emptyPage);

        ResultActions result = mockMvc.perform(get("/students/")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN","TEACHER"})
    void myGroup_shouldReturnForbiddenForNonStudentRole() throws Exception {

        SpecialityDto specialityDto = new SpecialityDto();
        specialityDto.setCode(121);
        specialityDto.setName("Name");

        GroupDto group = new GroupDto();
        group.setName("Group 1");
        group.setSpeciality(specialityDto);
        group.setStudents(new HashSet<StudentDto>());
        Page<GroupDto> page = new PageImpl<>(singletonList(group), PageRequest.of(0, 7), 1);

        when(groupService.findPage(anyInt(),anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/students/my-group")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isForbidden());

    }

    @Test
    @WithMockCustomUser(email = "student@gmail.com", role = Role.ROLE_STUDENT)
    void myGroup_shouldReturnCorrectViewWithAttributes() throws Exception {

        SpecialityDto specialityDto = new SpecialityDto();
        specialityDto.setCode(121);
        specialityDto.setName("Name");
        specialityDto.setDescription("");
        specialityDto.setId(1L);

        UserDto user = new UserDto();
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));

        StudentDto studentDto = new StudentDto();
        studentDto.setId(2L);
        studentDto.setUser(user);

        GroupDto groupDto = new GroupDto();
        groupDto.setId(1L);
        groupDto.setName("Group Name");
        groupDto.setSpeciality(specialityDto);
        groupDto.setStudents(Set.of(studentDto));

        Student student = new Student();
        student.setId(1L);
        Group group = new Group();
        group.setId(1L);
        student.setGroup(group);

        Pageable pageable = PageRequest.of(1,5);

        when(studentService.getCurrentStudent()).thenReturn(student);
        when(studentService.findByGroupIdExcludingByStudentId(1L, 1L, pageable ))
                .thenReturn(new PageImpl<>(List.of(studentDto)));
        when(groupMapper.toDto(group)).thenReturn(groupDto);

        mockMvc.perform(get("/students/my-group")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("/student/my-group"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("group", groupDto))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }



    @Test
    @WithMockUser(roles = {"ADMIN", "TEACHER"})
    void myGroup_shouldReturnForbidden_whenNoStudentRole() throws Exception {

        ResultActions result = mockMvc.perform(get("/students/my-group")
                .param("page", "0")
                .param("size", "5"));

        result.andExpect(status().isForbidden());
    }

}
