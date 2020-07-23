package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {

    public static final String FILES_ACTIVE_KEY = "filesActive";

    @Autowired
    private FileService fileService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String home(Model model) {
        String userId = userService.getCurrentUserId();

        model.addAttribute("filenames",
                fileService.getFiles(userId)
                        .stream()
                        .map(File::getFilename)
                        .collect(Collectors.toList()));

        model.addAttribute("notes", noteService.getNotes(userId));
        model.addAttribute("credentials", credentialsService.getCredentials(userId));

        updateActiveTab(model);

        // TODO - QUESTION - it is exposed for decrypting the password - is this the right way?
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    private void updateActiveTab(Model model) {
        if (!model.containsAttribute(NoteController.NOTES_ACTIVE_KEY) &&
                !model.containsAttribute(CredentialsController.CREDENTIALS_ACTIVE_KEY)) {
            model.addAttribute(FILES_ACTIVE_KEY, true);
        }
    }
}
