package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.dto.display.SpecialityDto;
import com.foxminded.korniichyk.university.security.CustomUserDetailsService;
import com.foxminded.korniichyk.university.security.SecurityConfig;
import com.foxminded.korniichyk.university.service.contract.SpecialityService;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@WebMvcTest(SpecialityController.class)

public class SpecialityControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecialityService specialityService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN", "STUDENT","TEACHER"})    void specialities_shouldReturnCorrectViewWithAttributes() throws Exception {
        SpecialityDto specialityDto = new SpecialityDto();
        specialityDto.setName("");
        specialityDto.setDescription("");
        specialityDto.setId(1L);
        specialityDto.setCode(100);

        Page<SpecialityDto> page = new PageImpl<>(Collections.singletonList(specialityDto), PageRequest.of(0, 7), 1);

        when(specialityService.findPage(anyInt(),anyInt())).thenReturn(page);

        ResultActions result = mockMvc.perform(get("/specialities/")
                .param("pageNumber", "0")
                .param("pageSize", "7"));

        result.andExpect(status().isOk())
                .andExpect(view().name("specialities"))
                .andExpect(model().attributeExists("specialities"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalElements"))
                .andExpect(model().attribute("specialities", page))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalElements", 1L));
    }

}
