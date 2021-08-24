package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.InternalServerError;
import com.example.demo.exception.MediaNotFoundException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.AvatarStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@Controller
@RequestMapping("/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    private final AvatarStorageService avatarStorageService;

    public ProfileController(AvatarStorageService avatarStorageService) {
        this.avatarStorageService = avatarStorageService;
    }

    @GetMapping()
    public String profileForm(Principal principal, Model model) {
        model.addAttribute("user", new UserDto());
        return "profile_form";
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
