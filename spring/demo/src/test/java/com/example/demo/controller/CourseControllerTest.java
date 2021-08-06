package com.example.demo.controller;


import com.example.demo.domain.Course;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import com.example.demo.service.StatisticsCounter;
import com.example.demo.service.UserService;
import com.example.demo.utils.MapptingUtils.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @MockBean
    private  CourseService courseService;

    @MockBean
    private  UserService userService;

    @MockBean
    private  StatisticsCounter statisticsCounter;

    @MockBean
    private  LessonService lessonService;

    @MockBean
    private  UserMapper userMapper;


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void courseTableTest() throws Exception {
        mockMvc.perform(get("/course"))
                .andExpect(status().isOk());
    }

    @Test
    public void courseFormTest() throws Exception {
        Mockito.when(courseService.courseById(1L)).thenReturn(new Course(1L,"Author","Title"));

        mockMvc.perform(get("/{id}",1L))
                .andExpect(status().isOk());
//                .andExpect();
    }
}
