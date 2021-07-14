package com.example.demo.controller;

import com.example.demo.domain.Course;
import com.example.demo.service.CourseLister;
import com.example.demo.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping ("/course")
public class CourseController {

    private final CourseLister courseLister;
    private final StatisticsCounter statisticsCounter;

    @Autowired
    public CourseController(CourseLister courseLister, StatisticsCounter statisticsCounter){
                this.courseLister = courseLister;
                this.statisticsCounter = statisticsCounter;
    }

    @GetMapping
    public String courseTable(Model model, @RequestParam(name = "titlePrefix",required = false) String titlePrefix){
        statisticsCounter.countHandlerCall("/course");
        model.addAttribute("courses",courseLister.
                courseByTitlePrefix(titlePrefix == null ? "" : titlePrefix));
        return "course_table";
    }

    @GetMapping("/{id}")
    public String courseForm (Model model, @PathVariable ("id") Long id) throws NotFoundException {
        statisticsCounter.countHandlerCall("/course/{id}");
        model.addAttribute("course", courseLister.courseById(id));
        return "course_form";
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFoundExceptionHandler(){
        statisticsCounter.countHandlerCall("/course_not_found");
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @GetMapping("/new")
    public String courseForm(Model model){
        statisticsCounter.countHandlerCall("/course/new");
        model.addAttribute("course", new Course());
        return "course_form";
    }
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id){
        statisticsCounter.countHandlerCall("/course/{id} - delete");
        courseLister.deleteCourse(id);
        return "redirect:/course";
    }

    @PostMapping(params = {"submit"})
    public String submitCourseForm(@Valid Course course, BindingResult bindingResult){
        statisticsCounter.countHandlerCall("/course/submit");
        if (bindingResult.hasErrors()){
            return "course_form";
        }
        courseLister.saveCourse(course);
        return "redirect:/course";
    }
}
