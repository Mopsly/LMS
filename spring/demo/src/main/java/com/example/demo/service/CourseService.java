package com.example.demo.service;

import com.example.demo.dao.CourseRepository;
import com.example.demo.domain.Course;
import com.example.demo.controller.NotFoundException;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseService {
    private  final CourseRepository repository;

    @Autowired
    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course courseById(Long id){
            return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Course> courseByTitlePrefix(String prefix){
        return repository.findByTitleLike(prefix + "%");
    }
    public void saveCourse(Course course){
        repository.save(course);
    }

    public List<Course> coursesByAuthor(String name){
        List <Course> allCourses= repository.findAll();
        return allCourses.stream().filter(course -> course.getAuthor().equals(name)).collect(Collectors.toList());
    }

    public void deleteCourse(Long id) {
        repository.deleteById(id);
    }

    public void setUserOnCourse(User user,Course course){
        course.getUsers().add(user);
        user.getCourses().add(course);
        repository.save(course);
    }
    public void removeUserFromCourse(User user,Course course){
        user.getCourses().remove(course);
        course.getUsers().remove(user);
        repository.save(course);
    }
}
