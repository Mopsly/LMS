package com.example.demo.controller;

import com.example.demo.exception.AccessDeniedException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"/admin"})
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String userTable(Model model, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            throw new AccessDeniedException();
        }
        model.addAttribute("users", this.userService.findAll());
        return "users_table";
    }
}