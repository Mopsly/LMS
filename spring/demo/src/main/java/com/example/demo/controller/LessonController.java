package com.example.demo.controller;

import com.example.demo.domain.Course;
import com.example.demo.domain.Lesson;
import com.example.demo.dto.LessonDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private final StatisticsCounter statisticsCounter;
    private final LessonService lessonService;
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public LessonController(StatisticsCounter statisticsCounter,
                            CourseService courseLister,
                            LessonService lessonService, UserService userService){
        this.statisticsCounter = statisticsCounter;
        this.courseService = courseLister;
        this.lessonService = lessonService;
        this.userService = userService;
    }



    @GetMapping("/new")
    public String lessonForm(Model model, @RequestParam("course_id") long courseId) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("lesson", new LessonDto(courseId));
        return "lesson_form";
    }

    @GetMapping("/{id}")
    public String lessonForm (Model model, @PathVariable("id") Long id){
        statisticsCounter.countHandlerCall("/lesson/{id}");
        model.addAttribute("lesson", LessonMapper.mapLessonToDto(lessonService.lessonById(id)));
        return "lesson_form";

    }

    @PostMapping(params = {"submit"})
    public String submitLessonForm(LessonDto lessonDto,Model model, BindingResult bindingResult){
        statisticsCounter.countHandlerCall("/lesson/submit");
        if (bindingResult.hasErrors()){
            return "lesson_form";
        }
        Course course = courseService.courseById(lessonDto.getCourseId());
        Lesson lesson = LessonMapper.mapDtoToLesson(lessonDto,course);
        lessonService.saveLesson(lesson);
        model.addAttribute("course", CourseMapper.mapLessonToDto(course));
        model.addAttribute("lessons",lessonService.lessonsWithoutText(course.getId()));
        model.addAttribute("users",course.getUsers());
        return "course_form";
    }

    @DeleteMapping("/{id}")
    public String deleteLesson(Model model,@PathVariable("id") Long id){
        statisticsCounter.countHandlerCall("/course/{id} - delete");
        Course course = lessonService.lessonById(id).getCourse();
        lessonService.deleteLesson(id);
        model.addAttribute("course", CourseMapper.mapLessonToDto(course));
        model.addAttribute("lessons",lessonService.lessonsWithoutText(course.getId()));
        model.addAttribute("users",course.getUsers());
        return "course_form";
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFoundExceptionHandler(){
        statisticsCounter.countHandlerCall("/course_not_found");
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
