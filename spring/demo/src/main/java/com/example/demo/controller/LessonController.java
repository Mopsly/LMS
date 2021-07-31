package com.example.demo.controller;

import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import com.example.demo.service.StatisticsCounter;
import com.example.demo.service.UserService;
import com.example.demo.utils.MapptingUtils.CourseMapper;
import com.example.demo.utils.MapptingUtils.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"/lesson"})
public class LessonController {
    private final StatisticsCounter statisticsCounter;
    private final LessonService lessonService;
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public LessonController(StatisticsCounter statisticsCounter, CourseService courseLister, LessonService lessonService, UserService userService) {
        this.statisticsCounter = statisticsCounter;
        this.courseService = courseLister;
        this.lessonService = lessonService;
        this.userService = userService;
    }

    @GetMapping({"/new"})
    public String lessonForm(Model model, @RequestParam("course_id") long courseId,
                             HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            throw new AccessDeniedException();
        }
        model.addAttribute("courseId", courseId);
        model.addAttribute("lesson", new LessonDto(courseId));
        return "lesson_form";
    }

    @GetMapping({"/{id}"})
    public String lessonForm(Model model, @PathVariable("id") Long id) {
        this.statisticsCounter.countHandlerCall("/lesson/{id}");
        model.addAttribute("lesson", LessonMapper.mapLessonToDto(this.lessonService.lessonById(id)));
        return "lesson_form";
    }

    @PostMapping(params = {"submit"})
    public String submitLessonForm(LessonDto lessonDto, Model model,
                                   BindingResult bindingResult,
                                   HttpServletRequest request) {
        this.statisticsCounter.countHandlerCall("/lesson/submit");
        if (!request.isUserInRole("ROLE_ADMIN")) {
            throw new AccessDeniedException();
        }
        if (bindingResult.hasErrors()) {
            return "lesson_form";
        } else {
            Course course = this.courseService.courseById(lessonDto.getCourseId());
            Lesson lesson = LessonMapper.mapDtoToLesson(lessonDto, course);
            this.lessonService.saveLesson(lesson);
            model.addAttribute("course", CourseMapper.mapLessonToDto(course));
            model.addAttribute("lessons", this.lessonService.lessonsWithoutText(course.getId()));
            model.addAttribute("users", course.getUsers());
            return "course_form";
        }
    }

    @DeleteMapping({"/{id}"})
    public String deleteLesson(Model model, @PathVariable("id") Long id,HttpServletRequest request) {
        this.statisticsCounter.countHandlerCall("/course/{id} - delete");
        if (!request.isUserInRole("ROLE_ADMIN")) {
            throw new AccessDeniedException();
        }
        Course course = this.lessonService.lessonById(id).getCourse();
        this.lessonService.deleteLesson(id);
        model.addAttribute("course", CourseMapper.mapLessonToDto(course));
        model.addAttribute("lessons", this.lessonService.lessonsWithoutText(course.getId()));
        model.addAttribute("users", course.getUsers());
        return "course_form";
    }
}
