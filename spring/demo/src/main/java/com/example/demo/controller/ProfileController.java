package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.InternalServerError;
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
import java.security.Principal;


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
    public String profileForm(Principal principal, Model model, Authentication auth) {
        model.addAttribute("user", userService.getUserByUsername(auth.getName()));
        model.addAttribute("courses", userService.getUserByUsername(auth.getName()).getCourses());
        return "profile_form";
    }

    @PostMapping
    public String updateProfile(@Valid @ModelAttribute("user") UserDto user,
                                BindingResult bindingResult,
                                @RequestParam("avatar") MultipartFile avatar,
                                Model model,
                                HttpServletRequest req) {
        model.addAttribute("courses",userService.findDtoById(user.getId()).getCourses());
        if (bindingResult.hasErrors()) {
            return "profile_form";
        } else {
            if (avatar!=null && avatar.getOriginalFilename().length()>0) {
                try {
                    avatarStorageService.save(req.getRemoteUser(), avatar.getContentType(), avatar.getInputStream());
                } catch (Exception ex) {
                    throw new InternalServerError();
                }
                userService.update(user);
            }
            try {
                req.logout();
            } catch (ServletException e) {
                e.printStackTrace();
            }
            return "profile_update_success";
        }
    }

    @PostMapping("/avatar")
    public String updateAvatarImage(Authentication auth,
                                    @RequestParam("avatar") MultipartFile avatar) {
        logger.info("File name {}, file content type {}, file size {}",
                avatar.getOriginalFilename(), avatar.getContentType(), avatar.getSize());
        try {
           Long id = userService.getUserByUsername(auth.getName()).getId();
            avatarStorageService.save(auth.getName(), avatar.getContentType(), avatar.getInputStream());
        } catch (Exception ex) {
            logger.info("", ex);
            throw new InternalServerError();
        }
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
