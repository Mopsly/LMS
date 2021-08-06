package com.example.demo.mappingUtils;

import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;
import com.example.demo.utils.MapptingUtils.LessonMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LessonMapperTest {

    @Test
    public void lessonToDtoTest(){
        Lesson lesson = new Lesson(1L,"Title","Text",
                new Course(2L,"Course_author","Course_title"));
        LessonDto lessonDto = LessonMapper.mapLessonToDto(lesson);

        Assertions.assertThat(lessonDto.getId()).isEqualTo(lesson.getId());
        Assertions.assertThat(lessonDto.getTitle()).isEqualTo(lesson.getTitle());
        Assertions.assertThat(lessonDto.getText()).isEqualTo(lesson.getText());
        Assertions.assertThat(lessonDto.getCourseId()).isEqualTo(lesson.getCourse().getId());
    }

    @Test
    public void dtoToLessonTest(){
        LessonDto lessonDto = new LessonDto(1L,2L,"Title","Text");
        Lesson lesson = LessonMapper.mapDtoToLesson(lessonDto, new Course(2L,"Course_author","Course_title"));

        Assertions.assertThat(lesson.getId()).isEqualTo(lessonDto.getId());
        Assertions.assertThat(lesson.getTitle()).isEqualTo(lessonDto.getTitle());
        Assertions.assertThat(lesson.getText()).isEqualTo(lessonDto.getText());
        Assertions.assertThat(lesson.getCourse().getId()).isEqualTo(lessonDto.getCourseId());
    }
}
