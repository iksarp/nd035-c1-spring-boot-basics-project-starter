package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

    private static final String FILE_SUCCESS_KEY = "fileSuccess";
    private static final String FILE_ERROR_KEY = "fileError";

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @PostMapping("/fileUpload")
    String uploadFile(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute(FILE_ERROR_KEY, "Empty file");
        } else {
            addFile(file, redirectAttributes);
        }
        return "redirect:/home";
    }

    @PostMapping("/delete")
    String deleteFile(@RequestParam("file") String filename, RedirectAttributes redirectAttributes) {
        try {
            fileService.deleteFile(filename);
            redirectAttributes.addFlashAttribute(FILE_SUCCESS_KEY, "File deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(FILE_ERROR_KEY, e.getMessage());
        }
        return "redirect:/home";
    }

    private void addFile(MultipartFile file, RedirectAttributes redirectAttributes) {
//        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            File newFile = new File(null, file.getOriginalFilename(), file.getContentType(), file.getBytes());//, user.getUserId());
            fileService.addFile(newFile);
            redirectAttributes.addFlashAttribute(FILE_SUCCESS_KEY, "File uploaded.");
        } catch (Exception e) {
            // TODO return more meaningful error not exposing code structure
            redirectAttributes.addFlashAttribute(FILE_ERROR_KEY, e.getMessage());
        }
    }
}
