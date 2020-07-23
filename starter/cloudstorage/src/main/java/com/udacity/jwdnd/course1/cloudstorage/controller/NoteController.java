package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {

    private static final String NOTE_SUCCESS_KEY = "noteSuccess";
    private static final String NOTE_ERROR_KEY = "noteError";

    public static final String NOTES_ACTIVE_KEY = "notesActive";

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @PostMapping("/notes/add")
    String addNote(String noteId, String noteTitle, String noteDescription, RedirectAttributes redirectAttributes) {
        String userId = userService.getCurrentUserId();
        try {
            // TODO I really don't like how to detect if it should be added or updated.
            // TODO How should I manage the noteModal so that action name can be parametrized?
            if (noteId.isEmpty()) {
                noteService.addNote(userId, noteTitle, noteDescription);
                redirectAttributes.addFlashAttribute(NOTE_SUCCESS_KEY, "Note added.");
            } else {
                noteService.updateNote(userId, noteId, noteTitle, noteDescription);
                redirectAttributes.addFlashAttribute(NOTE_SUCCESS_KEY, "Note updated.");
            }
        } catch (Exception e) {
            // TODO return more meaningful error not exposing potential code structure
            redirectAttributes.addFlashAttribute(NOTE_ERROR_KEY, e.getMessage());
        }
        redirectAttributes.addFlashAttribute(NOTES_ACTIVE_KEY, true);
        return "redirect:/home";
    }

    @PostMapping("/notes/delete")
    String deleteNote(String noteId, RedirectAttributes redirectAttributes) {
        String userId = userService.getCurrentUserId();
        try {
            noteService.deleteNote(userId, noteId);
            redirectAttributes.addFlashAttribute(NOTE_SUCCESS_KEY, "Note deleted.");
        } catch (Exception e) {
            // TODO return more meaningful error not exposing potential code structure
            redirectAttributes.addFlashAttribute(NOTE_ERROR_KEY, e.getMessage());
        }
        redirectAttributes.addFlashAttribute(NOTES_ACTIVE_KEY, true);
        return "redirect:/home";
    }
}
