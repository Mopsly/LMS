package com.example.demo.mappingUtils;

import com.example.demo.domain.Course;
import com.example.demo.dto.CourseDto;
import com.example.demo.utils.MapptingUtils.CourseMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseMapperTest {

    private final CourseMapper mapper;

    @Autowired
    public CourseMapperTest(CourseMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    public void courseToDtoTest(){
        Course course = new Course(1L,"Author","Title","Category");
        CourseDto courseDto = mapper.mapCourseToDto(course);
        Assertions.assertThat(courseDto.getAuthor()).isEqualTo(course.getAuthor());
        Assertions.assertThat(courseDto.getTitle()).isEqualTo(course.getTitle());
        Assertions.assertThat(courseDto.getId()).isEqualTo(course.getId());
    }

    @Test
    public void dtoToCourseTest(){
        CourseDto courseDto = new CourseDto(1L,"Title","Author","Category");
        Course course = mapper.mapDtoToCourse(courseDto);
        Assertions.assertThat(course.getAuthor()).isEqualTo(courseDto.getAuthor());
        Assertions.assertThat(course.getTitle()).isEqualTo(courseDto.getTitle());
        Assertions.assertThat(course.getId()).isEqualTo(courseDto.getId());
    }
}
