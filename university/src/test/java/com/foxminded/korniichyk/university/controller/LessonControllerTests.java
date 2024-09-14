package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.DisciplineDto;
import com.foxminded.korniichyk.university.dto.display.LessonDto;
import com.foxminded.korniichyk.university.dto.display.LessonTypeDto;
import com.foxminded.korniichyk.university.security.CustomUserDetailsService;
import com.foxminded.korniichyk.university.security.SecurityConfig;
import com.foxminded.korniichyk.university.service.contract.LessonService;
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
import java.time.LocalTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@WebMvcTest(LessonController.class)
public class LessonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN","STUDENT","TEACHER"})
    void lessons_shouldReturnCorrectViewWithAttributes() throws Exception {
        LessonTypeDto lessonTypeDto = new LessonTypeDto();
        lessonTypeDto.setId(1L);
        lessonTypeDto.setName("");


        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("");

        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(1L);
        lessonDto.setLessonType(lessonTypeDto);
        lessonDto.setDiscipline(disciplineDto);
        lessonDto.setDate(LocalDate.now());
        lessonDto.setStartTime(LocalTime.now());
        lessonDto.setEndTime(LocalTime.now());
        lessonDto.setClassroomNumber(1);

        Page<LessonDto> page = new PageImpl<>(Collections.singletonList(lessonDto), PageRequest.of(0, 7), 1);

        when(lessonService.findPage(anyInt(),anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/lessons/")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("lessons"))
                .andExpect(model().attributeExists("lessons"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("lessons", page))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }
}
