

package com.example.demo.service;

import com.example.demo.dao.CourseRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.User;
import com.example.demo.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseService {
    private final CourseRepository repository;

    @Autowired
    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course courseById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Course> courseByTitlePrefix(String prefix) {
        return this.repository.findByTitleLike(prefix + "%");
    }

    public void saveCourse(Course course) {
        this.repository.save(course);
    }

    public List<Course> coursesByAuthor(String name) {
        List<Course> allCourses = this.repository.findAll();
        return allCourses.stream().filter((course) -> {
            return course.getAuthor().equals(name);
        }).collect(Collectors.toList());
    }

    public void deleteCourse(Long id) {
        this.repository.deleteById(id);
    }

    public void setUserOnCourse(User user, Course course) {
        course.getUsers().add(user);
        user.getCourses().add(course);
        this.repository.save(course);
    }

    public void removeUserFromCourse(User user, Course course) {
        user.getCourses().remove(course);
        course.getUsers().remove(user);
        this.repository.save(course);
    }

    public List<Course> findAll() {
        return this.repository.findAll();
    }
}
