package com.example.demo.mappingUtils;

import com.example.demo.domain.Course;
import com.example.demo.dto.CourseDto;
import com.example.demo.utils.MapptingUtils.CourseMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourseMapperTest {

    @Test
    public void courseToDtoTest(){
        Course course = new Course(1L,"Author","Title");
        CourseDto courseDto = CourseMapper.mapCourseToDto(course);
        Assertions.assertThat(courseDto.getAuthor()).isEqualTo(course.getAuthor());
        Assertions.assertThat(courseDto.getTitle()).isEqualTo(course.getTitle());
        Assertions.assertThat(courseDto.getId()).isEqualTo(course.getId());
    }

    @Test
    public void dtoToCourseTest(){
        CourseDto courseDto = new CourseDto(1L,"Title","Author");
        Course course = CourseMapper.mapDtoToCourse(courseDto);
        Assertions.assertThat(course.getAuthor()).isEqualTo(courseDto.getAuthor());
        Assertions.assertThat(course.getTitle()).isEqualTo(courseDto.getTitle());
        Assertions.assertThat(course.getId()).isEqualTo(courseDto.getId());
    }
}
