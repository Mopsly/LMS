package com.example.demo.service;

import com.example.demo.dao.LessonRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.LessonService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class LessonServiceTest {

    LessonService lessonService;

    @BeforeEach
    public void setUp() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1L,"Урок 1","описание",new Course()));
        lessons.add(new Lesson(2L,"Урок 2","описание",new Course()));
        lessons.add(new Lesson(3L,"Урок 3","описание",new Course()));

        LessonRepository lessonRepositoryMock = Mockito.mock(LessonRepository.class);
        Mockito.when(lessonRepositoryMock.findAll()).thenReturn(lessons);
        Mockito.when(lessonRepositoryMock.findById(1L)).thenReturn(java.util.Optional.ofNullable(lessons.get(0)));
        Mockito.when(lessonRepositoryMock.findById(1000L)).thenReturn(java.util.Optional.ofNullable(null));

        lessonService = new LessonService(lessonRepositoryMock);
    }

    @Test
    public void findByIdTest(){
        Lesson lesson = lessonService.lessonById(1L);
        Assertions.assertThat(lesson.getId()).isEqualTo(1L);
        Assertions.assertThat(lesson.getTitle()).isEqualTo("Урок 1");
        Assertions.assertThat(lesson.getText()).isEqualTo("описание");
    }

    @Test
    public void findByIdNullTest(){
        try {
            Lesson lesson = lessonService.lessonById(1000L);
        }
        catch (NotFoundException e){
            Assertions.assertThat(true);
        }
    }
}
