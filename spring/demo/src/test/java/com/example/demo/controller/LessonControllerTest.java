package com.example.demo.controller;

import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import com.example.demo.service.StatisticsCounter;
import com.example.demo.service.UserService;
import com.example.demo.utils.MapptingUtils.LessonMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonController.class)
public class LessonControllerTest {

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private LessonMapper lessonMapperMock;

    @MockBean
    private StatisticsCounter statisticsCounter;

    @MockBean
    private CourseService courseService;

    @MockBean
    LessonService lessonService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void lessonFormTest() throws Exception {
        Lesson lesson = new Lesson(1L,"title","text",new Course());
        when(lessonService.lessonById(1L)).thenReturn(lesson);

        mockMvc.perform(get("/lesson/{id}",1L))
                .andExpect(view().name("lesson_form"))
                .andExpect(status().isOk());
    }
    @Test
    public void newLessonFormTest() throws Exception {

        mockMvc.perform(get("/lesson/new?course_id=1"))
                .andExpect(view().name("lesson_form"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitLessonFormTest() throws Exception {
            LessonDto lessonDto = new LessonDto(1L, 2L, "Title", "text");

            when(courseService.courseById(any(Long.class)))
                    .thenReturn(new Course(1L,"Author","title","category"));
            when(lessonService.lessonsWithoutText(any(Long.class))).thenReturn(new ArrayList<>());

            mockMvc.perform(post("/lesson")
                    .with(csrf())
                    .flashAttr("lessonDto", lessonDto))
                    .andExpect(model().hasNoErrors())
                    .andExpect(view().name("course_form"));
        }


    @Test
    void deleteLesson() throws Exception {
        Lesson actualLesson = new Lesson(1L, "title", "description", new Course(1L, "Author", "Title","category"));
        when(lessonService.lessonById(any())).thenReturn(actualLesson);
        doNothing().when(lessonService).deleteLesson(any());
        mockMvc.perform(delete("/lesson/{id}", 1L)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("course_form"));
        Mockito.verify(lessonService, Mockito.times(1)).deleteLesson(1L);
    }
}
