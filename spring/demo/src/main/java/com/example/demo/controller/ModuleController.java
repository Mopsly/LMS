package com.example.demo.controller;

import com.example.demo.dto.ModuleDto;
import com.example.demo.service.ModuleService;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/module")
public class ModuleController {

    private final CourseService courseService;
    private final ModuleService moduleService;

    @Autowired
    public ModuleController(CourseService courseService, ModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @GetMapping("/new")
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String moduleForm(Model model, HttpServletRequest request) {
        model.addAttribute("module", new ModuleDto());
        model.addAttribute("courses", courseService.findAll());
        return "module_form";
    }
}
