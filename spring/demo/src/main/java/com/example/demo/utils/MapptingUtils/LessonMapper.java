package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;

public class LessonMapper {
    public static Lesson mapDtoToLesson(LessonDto lessonDto, Course course) {

        return new Lesson(lessonDto.getId(), lessonDto.getTitle(), lessonDto.getText(), course);
    }

    public static LessonDto mapLessonToDto(Lesson lesson) {
        return new LessonDto(lesson.getId(), lesson.getCourse().getId(), lesson.getTitle(), lesson.getText());
    }
}
