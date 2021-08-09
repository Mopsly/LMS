package com.example.demo.controller;

import com.example.demo.Constants;
import com.example.demo.dao.RoleRepository;
import com.example.demo.domain.Course;
import com.example.demo.dto.UserDto;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import com.example.demo.service.StatisticsCounter;
import com.example.demo.service.UserService;
import com.example.demo.utils.MapptingUtils.CourseMapper;
import com.example.demo.utils.MapptingUtils.UserMapper;

import java.security.Principal;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

@Controller
@RequestMapping({"/course"})
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final StatisticsCounter statisticsCounter;
    private final LessonService lessonService;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);


    @Autowired
    public CourseController(CourseService courseService, UserService userService, StatisticsCounter statisticsCounter, LessonService lessonService, UserMapper userMapper) {
        this.courseService = courseService;
        this.userService = userService;
        this.statisticsCounter = statisticsCounter;
        this.lessonService = lessonService;
        this.userMapper = userMapper;
    }


    @GetMapping
    public String courseTable(Principal principal, Model model, @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        this.statisticsCounter.countHandlerCall("/course");
        if (principal != null) {
            logger.info("Request from user '{}'", principal.getName());
        }

        model.addAttribute("courses", this.courseService.courseByTitlePrefix(titlePrefix == null ? "" : titlePrefix));
        return "course_table";
    }

    @GetMapping({"/{id}"})
    public String courseForm(Model model, @PathVariable("id") Long id) {
        this.statisticsCounter.countHandlerCall("/course/{id}");
        Course course = this.courseService.courseById(id);
        model.addAttribute("course", CourseMapper.mapCourseToDto(course));
        model.addAttribute("lessons", this.lessonService.lessonsWithoutText(id));
        model.addAttribute("users", course.getUsers());
        return "course_form";
    }

    @GetMapping({"/new"})
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String courseForm(Model model, HttpServletRequest request) {
        this.statisticsCounter.countHandlerCall("/course/new");
        model.addAttribute("course", new Course());
        return "course_form";
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String deleteCourse(HttpServletRequest request, @PathVariable("id") Long id) {
        this.statisticsCounter.countHandlerCall("/course/{id} - delete");
        this.courseService.deleteCourse(id);
        return "redirect:/course";
    }

    @PostMapping
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String submitCourseForm(@Valid Course course, HttpServletRequest request, BindingResult bindingResult) {
        statisticsCounter.countHandlerCall("/course/submit");
        if (bindingResult.hasErrors()) {
            return "course_form";
        } else {
            this.courseService.saveCourse(course);
            return "redirect:/course";
        }
    }

    @GetMapping({"/{courseId}/assign"})
    @PreAuthorize("isAuthenticated()")
    public String assignCourse(Model model, HttpServletRequest request, @PathVariable Long courseId) {
        model.addAttribute("courseId", courseId);
        if (request.isUserInRole(Constants.ADMIN)) {
            model.addAttribute("users", this.userService.unassignedUsers(courseId));
        } else {
            UserDto user = this.userService.findUserByUsername(request.getRemoteUser());
            model.addAttribute("users", Collections.singletonList(user));
        }
        return "assign_course";
    }

    @PostMapping({"/{courseId}/assign"})
    public String assignUserForm(@PathVariable("courseId") Long courseId, @RequestParam("userId") Long id) {
        UserDto userDto = this.userService.findById(id);
        Course course = this.courseService.courseById(courseId);
        this.courseService.setUserOnCourse(this.userMapper.mapDtoToUser(userDto), course);
        return "redirect:/course/{courseId}";
    }

    @PostMapping({"/{courseId}/remove/{userId}"})
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String removeUserForm(@PathVariable("courseId") Long courseId, @PathVariable("userId") Long id, HttpServletRequest request) {
        UserDto userDto = this.userService.findById(id);
        Course course = this.courseService.courseById(courseId);
        this.courseService.removeUserFromCourse(this.userMapper.mapDtoToUser(userDto), course);
        return "redirect:/course/{courseId}";
    }
}

