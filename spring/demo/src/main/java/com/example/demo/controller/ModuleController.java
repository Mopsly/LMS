package com.example.demo.controller;

import com.example.demo.domain.Course;
import com.example.demo.domain.Module;
import com.example.demo.dto.ModuleDto;
import com.example.demo.service.ModuleService;
import com.example.demo.service.CourseService;
import com.example.demo.utils.MapptingUtils.ModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @GetMapping({"/{id}"})
    @PreAuthorize("@AccessSecurityBean.hasAccessToModule(#request, #id)")
    public String moduleForm(Model model, @PathVariable("id") Long id, HttpServletRequest request) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("module", ModuleMapper.mapModuleToDto(moduleService.findById(id)));
        return "module_form";
    }

    @PostMapping
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String submitModuleForm(@Valid @ModelAttribute("module") ModuleDto dto, BindingResult bindingResult,
                                   Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("courses", courseService.findAll());
            return "module_form";
        }
        Course course = courseService.courseById(dto.getCourseId());
        Module module = ModuleMapper.mapDtoToModule(dto, course);
        moduleService.saveModule(module);
        return String.format("redirect:/course/%d", course.getId());
    }
}
