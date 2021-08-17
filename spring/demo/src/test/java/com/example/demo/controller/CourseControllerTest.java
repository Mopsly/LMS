package com.example.demo.controller;


import com.example.demo.domain.Course;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import com.example.demo.service.StatisticsCounter;
import com.example.demo.service.UserService;
import com.example.demo.utils.MapptingUtils.UserMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @MockBean
    private UserService userService;

    @MockBean
    private StatisticsCounter statisticsCounter;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private UserMapper userMapper;


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void courseTableTest() throws Exception {
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course());
        courses.add(new Course());
        when(courseService.courseByTitlePrefix("")).thenReturn(courses);

        mockMvc.perform(get("/course"))
                .andExpect(status().isOk())
                .andExpect(view().name("course_table"))
                .andExpect(model().attribute("courses", courses));
    }

    @Test
    public void courseFormTest() throws Exception {
        Course testCourse = new Course(1L, "Author", "Title");
        when(courseService.courseById(1L)).thenReturn(testCourse);

        mockMvc.perform(get("/course/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("course_form"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void newCourseFromNew() throws Exception {
        Course testCourse = new Course(1L, "Author", "Title");
        when(courseService.courseById(any(Long.class))).thenReturn(testCourse);

        mockMvc.perform(get("/course/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("course_form"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void submitCourseFormTest() throws Exception {
        Course testCourse = new Course(1L, "Author", "Title");
        mockMvc
                .perform(post("/course")
                        .with(csrf())
                        .flashAttr("course", testCourse))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course"));
    }

    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void submitCourseFormByStudent() throws Exception {
        mockMvc
                .perform(post("/course").flashAttr("course", new Course()))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access_denied"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteCourseTest() throws Exception {
        mockMvc
                .perform(delete("/course/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course"));
    }

    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    public void deleteCourseByStudent() throws Exception {
        mockMvc
                .perform(delete("/course/{id}", 1L))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access_denied"));
    }


    @Test
    public void assignCourseTest() throws Exception {
        Course testCourse = new Course(1L, "Author", "Title");
        UserDto user = new UserDto(1L, "User","email","nickname", "password", new HashSet<>(), new HashSet<>());
        when(courseService.courseById(any(Long.class))).thenReturn(testCourse);

        when(userService.findUserByUsername(any())).thenReturn(user);
        mockMvc
                .perform(get("/course/{id}/assign", 1L)
                        .with(csrf())
                        .flashAttr("id", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("assign_course"));
    }

    @Test
    void removeUserFormTest() throws Exception {
        Course actualCourse = new Course();
        doNothing().when(courseService).removeUserFromCourse(any(), any());

        mockMvc.perform(post("/course/{courseId}/remove/{userId}", 1, 1)
                .with(csrf())
                .flashAttr("course", actualCourse))
                .andExpect(redirectedUrl("/course/1"))
                .andExpect(status().is3xxRedirection());
    }


}
