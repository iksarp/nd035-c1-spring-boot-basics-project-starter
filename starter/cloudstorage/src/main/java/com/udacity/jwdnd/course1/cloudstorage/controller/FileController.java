package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/fileUpload")
    String fileUpload(@RequestParam("fileUpload") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("fileUploadError", "Empty file");
        } else {
            addFile(file, model);
        }
        model.addAttribute("files", fileService.getFiles());
        return "home";
    }

    private void addFile(MultipartFile file, Model model) {
//        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            File newFile = new File(null, file.getOriginalFilename(), file.getContentType(), file.getBytes());//, user.getUserId());
            fileService.addFile(newFile);
            model.addAttribute("fileUploadSuccess", "Empty file");
        } catch (Throwable e) {
            // TODO return more meaningful error not exposing code structure
            model.addAttribute("fileUploadError", e.getMessage());
        }
    }
}
