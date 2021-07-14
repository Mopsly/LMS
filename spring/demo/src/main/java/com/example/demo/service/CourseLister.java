package com.example.demo.service;

import com.example.demo.dao.CourseRepository;
import com.example.demo.domain.Course;
import exceptions.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseLister {
    private  final CourseRepository repository;

    @Autowired
    public CourseLister(CourseRepository repository) {
        this.repository = repository;
    }

    public Course courseById(Long id) throws CourseNotFoundException {
        if(repository.findById(id).isPresent())
            return repository.findById(id).get();
        throw new CourseNotFoundException();
    }

    public List<Course> courseByTitlePrefix(String prefix){
        return repository.findByTitleWithPrefix(prefix);
    }
    public void saveCourse(Course course){
        repository.save(course);
    }

    public List<Course> coursesByAuthor(String name){
        List <Course> allCourses= repository.findAll();
        return allCourses.stream().filter(course -> course.getAuthor().equals(name)).collect(Collectors.toList());
    }

    public void deleteCourse(Long id) {
        repository.delete(id);
    }
}
