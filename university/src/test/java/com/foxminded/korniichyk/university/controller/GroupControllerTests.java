package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.GroupDto;
import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.dto.display.StudentDto;
import com.foxminded.korniichyk.university.security.CustomUserDetailsService;
import com.foxminded.korniichyk.university.security.SecurityConfig;
import com.foxminded.korniichyk.university.service.contract.GroupService;
import com.foxminded.korniichyk.university.service.contract.StudentService;
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

import java.util.HashSet;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupController.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class GroupControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private TeacherService teacherService;

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN","TEACHER"})
    void groups_shouldReturnCorrectViewWithAttributes() throws Exception {
        SpecialityDto specialityDto = new SpecialityDto();
        specialityDto.setCode(121);
        specialityDto.setName("Name");


        GroupDto group = new GroupDto();
        group.setName("Group 1");
        group.setSpeciality(specialityDto);
        group.setStudents(new HashSet<StudentDto>());
        Page<GroupDto> page = new PageImpl<>(singletonList(group), PageRequest.of(0, 7), 1);

        when(groupService.findPage(anyInt(),anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/groups/")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("groups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("groups", page))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }

    @Test
    @WithMockUser(username = "student@gmail.com", roles = {"STUDENT"})
    void groups_shouldReturnForbiddenForStudentRole() throws Exception {


        Page<GroupDto> page = Page.empty();

        when(groupService.findPage(anyInt(),anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/groups/")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isForbidden());
    }

}
