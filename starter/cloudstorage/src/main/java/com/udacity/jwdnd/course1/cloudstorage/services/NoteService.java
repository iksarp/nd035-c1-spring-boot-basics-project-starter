package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper mapper;

    public void addNote(String title, String description) throws Exception {
        if (title.isEmpty() || description.isEmpty()) {
            throw new Exception("Title and description must be not empty");
        }
        if (mapper.getNote(title) != null) {
            throw new Exception("Note with this title already exists");
        }
        if (mapper.insert(new Note(null, title, description)) < 0) {
            throw new Exception("Internal error: Could not add the note");
        }
    }

    public void deleteNote(String title) throws Exception {
        if (title.isEmpty()) {
            throw new Exception("Title and description must be not empty");
        }
        if (mapper.getNote(title) == null) {
            throw new Exception("No such note to delete");
        }
        if (mapper.delete(title) < 0) {
            throw new Exception("Internal error: Could not delete the note");
        }
    }

    public List<Note> getNotes() {
        return mapper.getNotes();
    }
}
