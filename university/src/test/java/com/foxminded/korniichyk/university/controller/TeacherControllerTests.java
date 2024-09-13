package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.annotation.WithMockCustomUser;
import com.foxminded.korniichyk.university.dto.display.*;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.model.Teacher;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.security.CustomUserDetailsService;
import com.foxminded.korniichyk.university.security.SecurityConfig;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
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
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@WebMvcTest(TeacherController.class)
public class TeacherControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN", "TEACHER", "STUDENT"})
    void teachers_shouldReturnCorrectViewWithAttributes() throws Exception {

        TeacherDto teacher = new TeacherDto();
        UserDto user = new UserDto();
        user.setId(1L);
        user.setFirstName("Joh");
        user.setLastName("Smith");
        user.setEmail("");
        teacher.setUser(user);
        teacher.setDisciplines(Set.of(new DisciplineDto()));
        Page<TeacherDto> page = new PageImpl<>(Collections.singletonList(teacher), PageRequest.of(0, 7), 1);

        when(teacherService.findPage(anyInt(), anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/teachers/")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("teachers"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("teachers", page))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }


    @Test
    @WithMockCustomUser(email = "email@gmail.com", role = Role.ROLE_TEACHER)
    void myGroups_shouldReturnCorrectViewWithAttributes() throws Exception {

        Teacher currentUserTeacher = new Teacher();
        currentUserTeacher.setId(1L);


        User user = new User();
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));

        SpecialityDto specialityDto = new SpecialityDto();
        specialityDto.setCode(121);
        specialityDto.setName("Name");
        specialityDto.setDescription("");
        specialityDto.setId(1L);

        GroupDto groupDto = new GroupDto();
        groupDto.setId(1L);
        groupDto.setSpeciality(specialityDto);
        groupDto.setName("Name");
        groupDto.setStudents(Set.of(new StudentDto()));

        currentUserTeacher.setUser(user);
        when(teacherService.findByUserId(anyLong())).thenReturn(currentUserTeacher);
        when(groupService.findPageByTeacherId(anyLong(), anyInt(), anyInt())).thenReturn(new PageImpl<>(Collections.singletonList(groupDto)));


        ResultActions result = mockMvc.perform(get("/teachers/my-groups")
                .param("page", "0")
                .param("size", "5"));

        result.andExpect(status().isOk())
                .andExpect(view().name("/teacher/my-groups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attribute("groups", singletonList(groupDto)))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1));

    }

    @Test
    @WithMockUser(roles = {"ADMIN", "STUDENT"})
    void myGroups_shouldReturnForbidden_withNonTeacherRole() throws Exception {

        ResultActions result = mockMvc.perform(get("/teachers/my-groups")
                .param("page", "0")
                .param("size", "5"));
        result.andExpect(status().isForbidden());
    }


}
