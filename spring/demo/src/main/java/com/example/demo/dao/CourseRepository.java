package com.example.demo.dao;

import com.example.demo.domain.Course;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTitleLike(String title);

    Optional<Course> findByTitle(String title);

    Optional<Course> findById(Long id);

}
