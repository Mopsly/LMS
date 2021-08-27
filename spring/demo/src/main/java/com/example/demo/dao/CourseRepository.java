package com.example.demo.dao;

import com.example.demo.domain.Course;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByTitleLike(String title, Pageable pageable);

    Page<Course> findByTitleLikeAndAuthor(String title, String author, Pageable pageable);

    Page<Course> findByTitleLikeAndCategory(String title, String category, Pageable pageable);

    Page<Course> findByTitleLikeAndAuthorAndCategory(String title,String author,String category, Pageable pageable);

    List<Course> findByTitleLike(String title);

    @Query("SELECT distinct  c.author from Course c")
    List<Optional<String>> findAllAuthors();

    @Query("SELECT distinct  c.category from Course c")
    List<Optional<String>> findAllCategories();

    Optional<Course> findByTitle(String title);

    Optional<Course> findById(Long id);

}
