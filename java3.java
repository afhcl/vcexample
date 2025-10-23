package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@RestController
public class AvatarUploadController {

    private static final Path AVATAR_UPLOAD_DIR = Paths.get("./uploads/avatars/").toAbsolutePath().normalize();

    @PostMapping("/upload-avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded or file is empty.");
        }

        // Get original filename safely
        String originalName = StringUtils.cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "avatar");
        if (originalName.contains("..")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid filename.");
        }

        // Ensure upload directory exists
        try {
            Files.createDirectories(AVATAR_UPLOAD_DIR);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create upload directory: " + e.getMessage());
        }

        // Resolve target path
        Path target = AVATAR_UPLOAD_DIR.resolve(originalName);

        // Save the file (overwrite if exists)
        try {
            // Optionally, you can use StandardCopyOption.REPLACE_EXISTING to overwrite
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save file: " + e.getMessage());
        }

        return ResponseEntity.ok("Uploaded avatar as: " + target.toString());
    }
}
``
