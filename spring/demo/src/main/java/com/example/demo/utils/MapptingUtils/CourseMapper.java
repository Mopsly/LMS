package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.LessonDto;

public class CourseMapper {
    public static Course mapDtoToCourse(CourseDto courseDto) {

        return new Course(courseDto.getId(), courseDto.getTitle(), courseDto.getAuthor());
    }

    public static CourseDto mapLessonToDto(Course course) {
        return new CourseDto(course.getId(),course.getTitle(), course.getAuthor());
    }
}
