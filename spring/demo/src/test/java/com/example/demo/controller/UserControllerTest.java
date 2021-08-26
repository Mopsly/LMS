package com.example.demo.controller;


import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.service.RoleService;
import com.example.demo.service.StatisticsCounter;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockBean
    private  UserService userService;

    @MockBean
    private  StatisticsCounter statisticsCounter;

    @MockBean
    private  RoleService roleService;


    @Autowired
    MockMvc mockMvc;

    @Test
    public void userFormTest() throws Exception{
        UserDto user = new UserDto(1L,"user","email", "nickname" ,"password","password",new HashSet<>(),new HashSet<>(), "88005553535");
        when(userService.findUserByUsername(any())).thenReturn(user);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"));
    }
    @Test
    public void userFormByIdTest() throws Exception{
        UserDto user = new UserDto(1L,"user","email", "nickname" ,"password","password",new HashSet<>(),new HashSet<>(), "88005553535");
        when(userService.findById(any(Long.class))).thenReturn((User) user);
        mockMvc
                .perform(get("/user/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"));
    }

    @Test
    public void newUserTest() throws Exception {
        mockMvc
                .perform(get("/user/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("reg_form"));
    }

    @Test
    public void submitNewUserLoginErrorTest() throws Exception {
        UserDto user = new UserDto(1L,"admin","email","nickname","123","123",new HashSet<>(),new HashSet<>(), "88005553535");

        mockMvc
                .perform(post("/user/new")
                        .with(csrf())
                        .flashAttr("user",user))
                .andExpect(view().name("access_denied"));
    }

    @Test
    public void submitUserFormTest() throws Exception {
        UserDto user = new UserDto(1L,"admin","email","nickname","123","123",new HashSet<>(),new HashSet<>(), "88005553535");
        mockMvc
                .perform(post("/user")
                        .with(csrf())
                        .flashAttr("user",user))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/course"));
    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteById(any(Long.class));
        mockMvc.perform(delete("/user/{id}", 1L)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
        Mockito.verify(userService, Mockito.times(1)).deleteById(1L);

    }

}
