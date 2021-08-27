

package com.example.demo.service;

import com.example.demo.dao.CourseRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.Course;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.utils.filter.CourseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Course> courseByTitlePrefix(String prefix, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        return this.repository.findByTitleLike(prefix + "%", pageable);
    }

    public Page<Course> coursesByFilters(CourseFilter filter, int page, int size) {
        String sortOrder = filter.getSortOrder() == null ? "title" : filter.getSortOrder();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder).ascending());
        if (filter.getCategory() == null) {
            filter.setCategory("any");
        }
        if (filter.getAuthor() == null) {
            filter.setAuthor("any");
        }
        if (!filter.getCategory().equals("any") && !filter.getAuthor().equals("any")) {
            //            (by author and category)
            return repository.findByTitleLikeAndAuthorAndCategory(
                    (filter.getTitlePrefix() == null ? "" : filter.getTitlePrefix()) + "%",
                    filter.getAuthor(),
                    filter.getCategory(), pageable);

        }
        if (filter.getCategory().equals("any") && !filter.getAuthor().equals("any")) {
//      (by author)
            return repository.findByTitleLikeAndAuthor(
                    (filter.getTitlePrefix() == null ? "" : filter.getTitlePrefix()) + "%",
                    filter.getAuthor(), pageable);
        }
        if (!filter.getCategory().equals("any") && filter.getAuthor().equals("any")) {
//      (by category)
            return repository.findByTitleLikeAndCategory(
                    (filter.getTitlePrefix() == null ? "" : filter.getTitlePrefix()) + "%",
                    filter.getCategory(), pageable);
        }
        //просто по названию
        return repository.findByTitleLike((filter.getTitlePrefix() == null ? "" : filter.getTitlePrefix()) + "%", pageable);
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

    public List<String> findAllCategories() {
        return repository.findAllCategories().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<String> findAllAuthors() {
        return repository.findAllAuthors().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
