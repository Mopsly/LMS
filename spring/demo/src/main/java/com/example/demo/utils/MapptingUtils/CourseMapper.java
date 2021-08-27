package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.Course;
import com.example.demo.dto.CourseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {
    private final ModelMapper mapper;

    @Autowired
    public CourseMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Course mapDtoToCourse(CourseDto courseDto) {
        return courseDto == null? null: mapper.map(courseDto,Course.class);
    }

    public CourseDto mapCourseToDto(Course course) {
        return course == null? new CourseDto(): mapper.map(course,CourseDto.class);
    }
}
