package com.example.demo.service;

import com.example.demo.dao.CourseRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.assertThrows;

public class CourseServiceTest {
    private static CourseService courseService;


    @BeforeEach
    public void setUp() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1L,"Вася","Course one","category"));
        courses.add(new Course(2L,"Петя","Course two","category"));
        courses.add(new Course(3L,"Петя","Course three","category"));

        CourseRepository courseRepositoryMock = Mockito.mock(CourseRepository.class);
        Mockito.when(courseRepositoryMock.findAll()).thenReturn(courses);
        Mockito.when(courseRepositoryMock.findById(1L)).thenReturn(java.util.Optional.ofNullable(courses.get(0)));
        Mockito.when(courseRepositoryMock.findById(1000L)).thenReturn(java.util.Optional.ofNullable(null));

        courseService = new CourseService(courseRepositoryMock);
    }

    @Test
    public void findByIdTest(){
        Course course = courseService.courseById(1L);
        Assertions.assertThat(course.getId()).isEqualTo(1L);
        Assertions.assertThat(course.getAuthor()).isEqualTo("Вася");
        Assertions.assertThat(course.getTitle()).isEqualTo("Course one");
    }
    @Test
    public void findByIdNullTest(){
        assertThrows(NotFoundException.class, () -> courseService.courseById(1000L));
    }

    @Test
    public void findByAuthorTest(){
        List<Course> courses = courseService.coursesByAuthor("Петя");
        Assertions.assertThat(courses.size()).isEqualTo(2);
        Assertions.assertThat(courses).extracting("author").doesNotContain("Вася");
        Assertions.assertThat(courses).extracting("author").contains("Петя");
    }


    @Test
    public void setAndRemoveFromCourseTest(){
        // создаем пользователя и курс
        User user  = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setCourses(new HashSet<Course>());
        user.setRoles(new HashSet<Role>());

        Course course = new Course(1L,"Виталий","Курс","category");
        course.setUsers(new HashSet<User>());

        // назначаем пользователя на крус
        courseService.setUserOnCourse(user,course);

        // проверка назначения на курс
        Assertions.assertThat(user.getCourses()).isNotEmpty();
        Assertions.assertThat(course.getUsers()).isNotEmpty();

        // убираем пользователя с курса
        courseService.removeUserFromCourse(user,course);

        // проверка того что пользователь убран
        Assertions.assertThat(user.getCourses()).isEmpty();
        Assertions.assertThat(course.getUsers()).isEmpty();
    }
}
