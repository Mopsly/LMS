package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.Course;
import com.example.demo.dto.CourseDto;

public class CourseMapper {
    public CourseMapper() {
    }

    public static Course mapDtoToCourse(CourseDto courseDto) {
        return new Course(courseDto.getId(), courseDto.getAuthor(), courseDto.getTitle());
    }

    public static CourseDto mapCourseToDto(Course course) {
        return new CourseDto(course.getId(), course.getTitle(),course.getAuthor());
    }
}
