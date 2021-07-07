package com.example.demo.controller;

import com.example.demo.domain.Course;
import com.example.demo.service.CourseLister;
import com.example.demo.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/course")
public class CourseController {
    @Autowired
    private CourseLister courseLister;
    @Autowired
    private StatisticsCounter statisticsCounter;

    @GetMapping("/interesting")
    public List<Course> getInterestingCourses(){
        statisticsCounter.countHandlerCall();
        // у нас есть бизнес инсайт, что все интересные курсы написал Ваня
        return courseLister.coursesByAuthor("Вася");
    }
}
