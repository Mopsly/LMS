package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LessonMapper {

    private final ModelMapper mapper;

    @Autowired
    public LessonMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public  Lesson mapDtoToLesson(LessonDto lessonDto, Course course) {
        return lessonDto == null? null: mapper.map(lessonDto,Lesson.class);
    }

    public  LessonDto mapLessonToDto(Lesson lesson) {
        return new LessonDto(lesson.getId(), lesson.getCourse().getId(), lesson.getTitle(), lesson.getText());
    }
}
