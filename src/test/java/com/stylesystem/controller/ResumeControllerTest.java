package com.stylesystem.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.UserInfoDto;
import com.stylesystem.service.ResumeService;

@SpringBootTest
@AutoConfigureMockMvc
public class ResumeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResumeService resumeService;

    @Test
    public void testResumeView_ReturnsResumeView_WhenResumeExists() throws Exception {
        // given
        String userId = "testUser";
        
        // Creating a ResumeDto object with a UserInfoDto containing the userId
        ResumeDto resumeDto = ResumeDto.builder()
            .userInfo(UserInfoDto.builder().userId(userId).build())
            .build();

        when(resumeService.getResumeByUserId(userId)).thenReturn(resumeDto);

        // when, then
        mockMvc.perform(get("/resume/view")
                .param("userId", userId))
                // .andExpect(status().isOk())
                .andExpect(model().attributeExists("resumeDto"))
                .andExpect(view().name("resumeView"));
    }

    @Test
    public void testResumeView_ReturnsErrorPage_WhenResumeNotFound() throws Exception {
        // given
        String userId = "unknownUser";

        when(resumeService.getResumeByUserId(userId)).thenReturn(null);

        // when, then
        mockMvc.perform(get("/resume/view")
                .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("errorPage"));
    }
}
