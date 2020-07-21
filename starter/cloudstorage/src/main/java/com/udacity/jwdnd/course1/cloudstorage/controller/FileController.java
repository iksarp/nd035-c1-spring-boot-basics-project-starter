package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/upload")
    String uploadFile(@RequestParam("upload") MultipartFile file, RedirectAttributes redirectAttributes) {
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
            // TODO return more meaningful error not exposing potential code structure
            redirectAttributes.addFlashAttribute(FILE_ERROR_KEY, e.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/download")
    ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("file") String filename) {
        File file = fileService.getFile(filename);
        // TODO how to handle missing file? How to show it in HTML?
        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentType(MediaType.parseMediaType(file.getContentType())) //
                .contentLength(file.getFiledata().length)
                .body(resource);
    }

    private void addFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        // TODO user auth
//        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            File newFile = new File(null, file.getOriginalFilename(), file.getContentType(), file.getBytes());//, user.getUserId());
            fileService.addFile(newFile);
            redirectAttributes.addFlashAttribute(FILE_SUCCESS_KEY, "File uploaded.");
        } catch (Exception e) {
            // TODO return more meaningful error not exposing potential code structure
            redirectAttributes.addFlashAttribute(FILE_ERROR_KEY, e.getMessage());
        }
    }
}
