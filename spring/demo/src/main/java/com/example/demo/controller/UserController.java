package com.example.demo.controller;

import com.example.demo.domain.Role;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.InternalServerError;
import com.example.demo.exception.LoginFailedException;
import com.example.demo.service.AvatarStorageService;
import com.example.demo.service.RoleService;
import com.example.demo.service.StatisticsCounter;
import com.example.demo.service.UserService;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/user"})
public class UserController {
    private final UserService userService;
    private final StatisticsCounter statisticsCounter;
    private final RoleService roleService;
    private final AvatarStorageService avatarStorageService;

    public UserController(UserService userService, StatisticsCounter statisticsCounter, RoleService roleService, AvatarStorageService avatarStorageService) {
        this.userService = userService;
        this.statisticsCounter = statisticsCounter;
        this.roleService = roleService;
        this.avatarStorageService = avatarStorageService;
    }

    @GetMapping
    public String userForm(Model model, HttpServletRequest request) { this.statisticsCounter.countHandlerCall("/user");
        model.addAttribute("user", this.userService.findUserByUsername(request.getRemoteUser()));
        return "user_form";
    }

    @GetMapping({"{id}"})
    public String userForm(Model model, HttpServletRequest request, @PathVariable Long id) {
        this.statisticsCounter.countHandlerCall("/user");
        model.addAttribute("user", this.userService.findById(id));
        return "user_form";
    }

    @PostMapping
    public String submitUserForm(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_form";
        } else {
            this.userService.save(user);
            return "redirect:/course";
        }
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("@AccessSecurityBean.hasAdminRights(#request)")
    public String deleteUser(HttpServletRequest request, @PathVariable("id") Long id) {
        this.statisticsCounter.countHandlerCall("/user/{id} - delete");
        this.userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @ModelAttribute("roles")
    public List<Role> rolesAttribute() {
        return this.roleService.findAll();
    }
}
