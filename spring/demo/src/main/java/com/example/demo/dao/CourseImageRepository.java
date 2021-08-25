package com.example.demo.dao;

import com.example.demo.domain.AvatarImage;
import com.example.demo.domain.CourseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseImageRepository extends JpaRepository<CourseImage,Long> {
    Optional <CourseImage> findByCourse_Id(Long id);
}
