package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FileUploadController {

    @PostMapping("/fileUpload")
    String fileUpload() {
        return "home";
    }
}