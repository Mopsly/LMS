package com.example.demo.controller;

import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"/admin"})
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public AdminController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String adminPanel(Model model,
                             @RequestParam(name = "titlePrefix", required = false) String titlePrefix,
                             HttpServletRequest request) {
        model.addAttribute("courses", courseService.courseByTitlePrefix(titlePrefix == null ? "" : titlePrefix));
        return "admin_course_table";
    }

    @GetMapping("/users")
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String userTable(Model model, HttpServletRequest request) {
        model.addAttribute("users", userService.findAll());
        return "users_table";
    }
}