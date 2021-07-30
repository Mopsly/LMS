package com.example.demo.controller;

import com.example.demo.dao.RoleRepository;
import com.example.demo.domain.Role;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.service.RoleService;
import com.example.demo.service.StatisticsCounter;
import com.example.demo.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/user"})
public class UserController {
    private final UserService userService;
    private final StatisticsCounter statisticsCounter;
    private final RoleService roleService;

    public UserController(UserService userService, RoleRepository roleRepository, StatisticsCounter statisticsCounter, RoleService roleService) {
        this.userService = userService;
        this.statisticsCounter = statisticsCounter;
        this.roleService = roleService;
    }

    @GetMapping
    public String userForm(Model model, HttpServletRequest request) {
        this.statisticsCounter.countHandlerCall("/user");
        model.addAttribute("user", this.userService.findUserByUsername(request.getRemoteUser()));
        return "user_form";
    }

    @GetMapping({"{id}"})
    public String userForm(Model model, HttpServletRequest request, @PathVariable Long id) {
        this.statisticsCounter.countHandlerCall("/user");
        model.addAttribute("user", this.userService.findById(id));
        return "user_form";
    }

    @GetMapping({"/new"})
    public String regForm(Model model) {
        this.statisticsCounter.countHandlerCall("/user");
        model.addAttribute("user", this.roleService.setDefaultRole(new UserDto()));
        return "reg_form";
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
    public String deleteUser(HttpServletRequest request, @PathVariable("id") Long id) {
        this.statisticsCounter.countHandlerCall("/user/{id} - delete");
        if (!request.isUserInRole("ROLE_ADMIN")) {
            throw new AccessDeniedException();
        } else {
            this.userService.deleteById(id);
            return "redirect:/admin/users";
        }
    }

    @ModelAttribute("roles")
    public List<Role> rolesAttribute() {
        return this.roleService.findAll();
    }
}
