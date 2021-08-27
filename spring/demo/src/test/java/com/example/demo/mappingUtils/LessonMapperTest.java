package com.example.demo.mappingUtils;

import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;
import com.example.demo.utils.MapptingUtils.LessonMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LessonMapperTest {

    private final LessonMapper lessonMapper;

    @Autowired
    public LessonMapperTest(LessonMapper lessonMapper) {
        this.lessonMapper = lessonMapper;
    }

    @Test
    public void lessonToDtoTest(){
        Lesson lesson = new Lesson(1L,"Title","Text",
                new Course(2L,"Course_author","Course_title","category"));
        LessonDto lessonDto = lessonMapper.mapLessonToDto(lesson);

        Assertions.assertThat(lessonDto.getId()).isEqualTo(lesson.getId());
        Assertions.assertThat(lessonDto.getTitle()).isEqualTo(lesson.getTitle());
        Assertions.assertThat(lessonDto.getText()).isEqualTo(lesson.getText());
        Assertions.assertThat(lessonDto.getCourseId()).isEqualTo(lesson.getCourse().getId());
    }

    @Test
    public void dtoToLessonTest(){
        LessonDto lessonDto = new LessonDto(1L,2L,"Title","Text");
        Lesson lesson = lessonMapper.mapDtoToLesson(lessonDto, new Course(2L,"Course_author","Course_title","category"));

        Assertions.assertThat(lesson.getId()).isEqualTo(lessonDto.getId());
        Assertions.assertThat(lesson.getTitle()).isEqualTo(lessonDto.getTitle());
        Assertions.assertThat(lesson.getText()).isEqualTo(lessonDto.getText());
        Assertions.assertThat(lesson.getCourse().getId()).isEqualTo(lessonDto.getCourseId());
    }
}
