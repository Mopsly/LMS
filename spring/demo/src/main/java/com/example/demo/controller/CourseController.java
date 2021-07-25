package com.example.demo.controller;

import com.example.demo.domain.Course;
import com.example.demo.domain.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import com.example.demo.service.StatisticsCounter;
import com.example.demo.service.UserService;
import com.example.demo.utils.MapptingUtils.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;
    private final StatisticsCounter statisticsCounter;
    private final LessonService lessonService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService, StatisticsCounter statisticsCounter, LessonService lessonService) {
        this.courseService = courseService;
        this.userService = userService;
        this.statisticsCounter = statisticsCounter;
        this.lessonService = lessonService;
    }

    @GetMapping
    public String courseTable(Model model, @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        statisticsCounter.countHandlerCall("/course");
        model.addAttribute("courses", courseService.
                courseByTitlePrefix(titlePrefix == null ? "" : titlePrefix));
        return "course_table";
    }

    @GetMapping("/{id}")
    public String courseForm(Model model, @PathVariable("id") Long id) {
        statisticsCounter.countHandlerCall("/course/{id}");
        Course course = courseService.courseById(id);
        model.addAttribute("course", CourseMapper.mapLessonToDto(course));
        model.addAttribute("lessons",lessonService.lessonsWithoutText(id));
        model.addAttribute("users",course.getUsers());
        return "course_form";
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        statisticsCounter.countHandlerCall("/course/new");
        model.addAttribute("course", new Course());
        return "course_form";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        statisticsCounter.countHandlerCall("/course/{id} - delete");
        courseService.deleteCourse(id);
        return "redirect:/course";
    }

    @PostMapping(params = {"submit"})
    public String submitCourseForm(@Valid Course course, BindingResult bindingResult) {
        statisticsCounter.countHandlerCall("/course/submit");
        if (bindingResult.hasErrors()) {
            return "course_form";
        }
        courseService.saveCourse(course);
        return "redirect:/course";
    }

    @GetMapping("/{courseId}/assign")
    public String assignCourse(Model model, @PathVariable Long courseId) {
        model.addAttribute("users", userService.unassignedUsers(courseId));
        return "assign_course";
    }

    @PostMapping("/{courseId}/assign")
    public String assignUserForm(@PathVariable("courseId") Long courseId,
                                 @RequestParam("userId") Long id) {
        User user = userService.userById(id);
        Course course = courseService.courseById(courseId);
        courseService.setUserOnCourse(user,course);
        return "redirect:/course/{courseId}";
    }

    @PostMapping("/{courseId}/remove/{userId}")
    public String removeUserForm(@PathVariable("courseId") Long courseId,
                                 @PathVariable("userId") Long id) {
        User user = userService.userById(id);
        Course course = courseService.courseById(courseId);
        courseService.removeUserFromCourse(user,course);
        return "redirect:/course/{courseId}";
    }
}
