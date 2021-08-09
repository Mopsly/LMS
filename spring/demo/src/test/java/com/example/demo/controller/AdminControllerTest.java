package com.example.demo.controller;


import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @MockBean
    private UserService userServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void unAuthorisedUsersTestU() throws Exception {
        ArrayList<UserDto> users = new ArrayList<>();
        when(userServiceMock.findAll()).thenReturn(users);
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
