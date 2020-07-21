package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {

    public static final String FILES_ACTIVE_KEY = "filesActive";

    @Autowired
    private FileService fileService;

    @Autowired
    private NoteService noteService;

    @GetMapping()
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("filenames",
                fileService.getFiles()
                        .stream()
                        .map(File::getFilename)
                        .collect(Collectors.toList()));

        model.addAttribute("notes", noteService.getNotes());

        if (!model.containsAttribute(NoteController.NOTES_ACTIVE_KEY) &&
                !model.containsAttribute(CredentialsController.CREDENTIALS_ACTIVE_KEY)) {
            model.addAttribute(FILES_ACTIVE_KEY, true);
        }

        return "home";
    }
}
