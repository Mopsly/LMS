package com.example.demo.service;

import com.example.demo.dao.LessonRepository;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;
import com.example.demo.exception.NotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LessonService {
    private final LessonRepository repository;

    @Autowired
    public LessonService(LessonRepository repository) {
        this.repository = repository;
    }

    public Lesson lessonById(Long id) {
        return (Lesson)this.repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void saveLesson(Lesson course) {
        this.repository.save(course);
    }

    public void deleteLesson(Long id) {
        this.repository.deleteById(id);
    }

    public List<LessonDto> lessonsWithoutText(Long id) {
        return this.repository.findAllForLessonIdWithoutText(id);
    }
}
