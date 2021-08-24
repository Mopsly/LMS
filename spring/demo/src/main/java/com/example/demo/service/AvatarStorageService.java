package com.example.demo.service;

import com.example.demo.dao.AvatarImageRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.domain.AvatarImage;
import com.example.demo.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.*;

@Service
public class AvatarStorageService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarStorageService.class);

    private final AvatarImageRepository avatarImageRepository;

    private final UserRepository userRepository;

    @Value("${file.storage.path}")
    private String path;

    @Autowired
    public AvatarStorageService(AvatarImageRepository avatarImageRepository, UserRepository userRepository) {
        this.avatarImageRepository = avatarImageRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void save(String username, MultipartFile avatar) {
        if (avatar == null || avatar.getOriginalFilename().length() == 0){
            return;
        }
        String contentType = avatar.getContentType();
        InputStream is = InputStream.nullInputStream();
        try {
            is = avatar.getInputStream();
        } catch (IOException ex) {
            logger.error("Can't read file {}", ex);
            throw new IllegalStateException(ex);
        }
        Optional<AvatarImage> opt = avatarImageRepository.findByUser_Username(username);
        AvatarImage avatarImage;
        String filename;
        if (opt.isEmpty()) {
            filename = UUID.randomUUID().toString();
            avatarImage = new AvatarImage(null, contentType, filename, userRepository.getUserByUsername(username));
        } else {
            avatarImage = opt.get();
            filename = avatarImage.getFilename();
            avatarImage.setContentType(contentType);
        }
        avatarImageRepository.save(avatarImage);

        try (OutputStream os = Files.newOutputStream(Path.of(path, filename), CREATE, WRITE, TRUNCATE_EXISTING)) {
            is.transferTo(os);
        } catch (IOException ex) {
            logger.error("Can't write to file {}", filename, ex);
            throw new IllegalStateException(ex);
        }
    }

    public Optional<String> getContentTypeByUser(String username) {
        return avatarImageRepository.findByUser_Username(username)
                .map(AvatarImage::getContentType);
    }

    public Optional<byte[]> getAvatarImageByUser(String username) {
        return avatarImageRepository.findByUser_Username(username)
                .map(AvatarImage::getFilename)
                .map(filename -> {
                    try {
                        return Files.readAllBytes(Path.of(path, filename));
                    } catch (IOException ex) {
                        logger.error("Can't read file {}", filename, ex);
                        throw new IllegalStateException(ex);
                    }
                });
    }
}
