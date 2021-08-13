package com.example.demo.service;

import com.example.demo.dao.CourseImageRepository;
import com.example.demo.dao.CourseRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.AvatarImage;
import com.example.demo.domain.Course;
import com.example.demo.domain.CourseImage;
import com.example.demo.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.*;

@Service
public class CourseImageStorageService {

    private static final Logger logger = LoggerFactory.getLogger(CourseImageStorageService.class);

    private final CourseImageRepository courseImageRepository;

    private final CourseRepository courseRepository;

    @Value("${file.storage.path}")
    private String path;

    @Autowired
    public CourseImageStorageService(CourseImageRepository avatarImageRepository, CourseRepository courseRepository) {
        this.courseImageRepository = avatarImageRepository;
        this.courseRepository = courseRepository;
    }
    @Transactional
    public void save(Long id, String contentType, InputStream is) {
        Optional<CourseImage> opt = courseImageRepository.findByCourse_Id(id);
        CourseImage courseImage;
        String filename;
        if (opt.isEmpty()) {
            filename = UUID.randomUUID().toString();
            Course course = courseRepository.findById(id)
                    .orElseThrow(IllegalArgumentException::new);
            courseImage = new CourseImage(null, contentType, filename, course);
        } else {
            courseImage = opt.get();
            filename = courseImage.getFilename();
            courseImage.setContentType(contentType);
        }
        courseImageRepository.save(courseImage);

        try (OutputStream os = Files.newOutputStream(Path.of(path, filename), CREATE, WRITE, TRUNCATE_EXISTING)) {
            is.transferTo(os);
        } catch (Exception ex) {
            logger.error("Can't write to file {}", filename, ex);
            throw new IllegalStateException(ex);
        }
    }

    public Optional<String> getContentTypeByCourse(Long id) {
        return courseImageRepository.findByCourse_Id(id)
                .map(CourseImage::getContentType);

    }

    public Optional<byte[]> getAvatarImageByCourse(Long id) {
        return courseImageRepository.findByCourse_Id(id)
                .map(CourseImage::getFilename)
                .map(filename -> {
                    try {
                        return Files.readAllBytes(Path.of(path, filename));
                    } catch (IOException ex) {
                        logger.error("Can't read file {}", filename, ex);
                        throw new IllegalStateException(ex);
                    }
                });
    }
}
