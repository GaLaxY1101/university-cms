package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.annotation.WithMockCustomUser;
import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.dto.display.TeacherDto;
import com.foxminded.korniichyk.university.dto.display.UserDto;
import com.foxminded.korniichyk.university.model.Role;
import com.foxminded.korniichyk.university.model.Teacher;
import com.foxminded.korniichyk.university.model.User;
import com.foxminded.korniichyk.university.security.CustomUserDetailsService;
import com.foxminded.korniichyk.university.security.SecurityConfig;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.TeacherService;
import com.foxminded.korniichyk.university.util.TestUtil;
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

import java.util.Collections;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
        UserDto user = TestUtil.generateUserDto();

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


        User user = TestUtil.generateUser();

        SpecialityDto specialityDto = TestUtil.generateSpecialityDto();

        GroupDto groupDto = TestUtil.generateGroupDto();
        groupDto.setSpeciality(specialityDto);
        groupDto.setStudents(Set.of(new StudentDto()));

        currentUserTeacher.setUser(user);
        when(teacherService.getCurrentTeacher()).thenReturn(currentUserTeacher);
        when(groupService.findByTeacherId(anyLong(), anyInt(), anyInt()))
                .thenReturn(new PageImpl<>(Collections.singletonList(groupDto)));


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
