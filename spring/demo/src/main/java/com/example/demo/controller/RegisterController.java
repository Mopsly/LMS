package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.InternalServerError;
import com.example.demo.exception.LoginFailedException;
import com.example.demo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping({"/register"})
public class RegisterController {
    private final UserService userService;
    private final StatisticsCounter statisticsCounter;
    private final RoleService roleService;
    private final AvatarStorageService avatarStorageService;
    private final AuthorisationService authorisationService;

    public RegisterController(UserService userService, StatisticsCounter statisticsCounter, RoleService roleService, AvatarStorageService avatarStorageService, AuthorisationService authorisationService) {
        this.userService = userService;
        this.statisticsCounter = statisticsCounter;
        this.roleService = roleService;
        this.avatarStorageService = avatarStorageService;
        this.authorisationService = authorisationService;
    }


    @GetMapping
    public String regForm(Model model) {
        this.statisticsCounter.countHandlerCall("/register");
        model.addAttribute("user", new UserDto());
        return "reg_form";
    }



    @PostMapping
    public String submitRegForm(@Valid @ModelAttribute("user") UserDto user,
                                @RequestParam("avatar") MultipartFile avatar,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "reg_form";
        } else {
            userService.save(roleService.setDefaultRole(user));
            if (avatar != null){
                try {
                    avatarStorageService.save(user.getUsername(), avatar.getContentType(), avatar.getInputStream());
                } catch (Exception ex) {
                    throw new InternalServerError();
                }
            }
            authorisationService.sendMessage(user.getEmail(),"Confirm your registration",
                    authorisationService.messageToSend(user, authorisationService.generateCode(user)));
            return "reg_confirm";
        }
    }

    @GetMapping("/{code}/success")
    public String registerSuccess(@PathVariable String code, HttpServletRequest request){
        UserDto user = authorisationService.findUserByCode(code);
        authorisationService.authoriseUser(user);
        return "reg_success";
    }
}
