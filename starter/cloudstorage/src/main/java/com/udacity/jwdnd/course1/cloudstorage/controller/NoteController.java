package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NoteController {

    private static final String NOTE_SUCCESS_KEY = "noteSuccess";
    private static final String NOTE_ERROR_KEY = "noteError";

    public static final String NOTES_ACTIVE_KEY = "notesActive";

    @Autowired
    private NoteService noteService;

    @PostMapping("/notes/add")
    String addNote(String noteTitle, String noteDescription, RedirectAttributes redirectAttributes) {
        try {
            noteService.addNote(noteTitle, noteDescription);
            redirectAttributes.addFlashAttribute(NOTE_SUCCESS_KEY, "Note added.");
        } catch (Exception e) {
            // TODO return more meaningful error not exposing potential code structure
            redirectAttributes.addFlashAttribute(NOTE_ERROR_KEY, e.getMessage());
        }
        redirectAttributes.addFlashAttribute(NOTES_ACTIVE_KEY, true);
        return "redirect:/home";
    }

    @PostMapping("/notes/delete")
    String deleteNote(String noteTitle, RedirectAttributes redirectAttributes) {
        try {
            noteService.deleteNote(noteTitle);
            redirectAttributes.addFlashAttribute(NOTE_SUCCESS_KEY, "Note deleted.");
        } catch (Exception e) {
            // TODO return more meaningful error not exposing potential code structure
            redirectAttributes.addFlashAttribute(NOTE_ERROR_KEY, e.getMessage());
        }
        redirectAttributes.addFlashAttribute(NOTES_ACTIVE_KEY, true);
        return "redirect:/home";
    }
}
