package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.MediaNotFoundException;
import com.example.demo.service.AvatarStorageService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    private final AvatarStorageService avatarStorageService;
    private final UserService userService;

    public ProfileController(AvatarStorageService avatarStorageService, UserService userService) {
        this.avatarStorageService = avatarStorageService;
        this.userService = userService;
    }

    @GetMapping()
    public String profileForm(Model model, Authentication auth) {
        model.addAttribute("user", userService.findUserByUsername(auth.getName()));
        model.addAttribute("courses", userService.findUserByUsername(auth.getName()).getCourses());
        return "profile_form";
    }

    @PostMapping
    public String updateProfile(@Valid @ModelAttribute("user") UserDto user,
                                BindingResult bindingResult,
                                @RequestParam("avatar") MultipartFile avatar,
                                Model model,
                                HttpServletRequest req) {
        model.addAttribute("courses", userService.findDtoById(user.getId()).getCourses());
        if (bindingResult.hasErrors()) {
            return "profile_form";
        } else {
            avatarStorageService.save(req.getRemoteUser(), avatar);
            userService.update(user);
        }
        try {
            req.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "profile_update_success";
    }

    @PostMapping("/avatar")
    public String updateAvatarImage(Authentication auth,
                                    @RequestParam("avatar") MultipartFile avatar) {
        logger.info("File name {}, file content type {}, file size {}",
                avatar.getOriginalFilename(), avatar.getContentType(), avatar.getSize());
        avatarStorageService.save(auth.getName(), avatar);
        return "redirect:/profile";
    }

    @GetMapping("/avatar")
    @ResponseBody
    public ResponseEntity<byte[]> avatarImage(Authentication auth) {
        String contentType = avatarStorageService.getContentTypeByUser(auth.getName())
                .orElseThrow(MediaNotFoundException::new);
        byte[] data = avatarStorageService.getAvatarImageByUser(auth.getName())
                .orElseThrow(MediaNotFoundException::new);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(data);
    }
}
