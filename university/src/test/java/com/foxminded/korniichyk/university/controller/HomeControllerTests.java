package com.foxminded.korniichyk.university.controller;

import com.foxminded.korniichyk.university.security.CustomUserDetailsService;
import com.foxminded.korniichyk.university.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"ADMIN","TEACHER","STUDENT"})
    void home_shouldReturnHomePage() throws Exception {

        ResultActions result = mockMvc.perform(get("/"));

        result.andExpect(status().isOk())
                .andExpect(view().name("home"));

    }
}
