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

    public void addNote(String userId, String title, String description) throws Exception {
        if (title.isEmpty() || description.isEmpty()) {
            throw new Exception("Title and description must be not empty");
        }
        if (mapper.insert(new Note(null, title, description, userId)) < 0) {
            throw new Exception("Internal error: Could not add the note");
        }
    }

    public void updateNote(String userId, int noteId, String title, String description) throws Exception {
        if (title.isEmpty() || description.isEmpty()) {
            throw new Exception("Title and description must be not empty");
        }
        if (mapper.update(userId, noteId, title, description) < 0) {
            throw new Exception("Internal error: Could not update the note");
        }
    }

    public void deleteNote(String userId, int noteId) throws Exception {
        if (getNote(userId, noteId) == null) {
            throw new Exception("No such note to delete");
        }
        if (mapper.delete(userId, noteId) < 0) {
            throw new Exception("Internal error: Could not delete the note");
        }
    }

    public Note getNote(String userId, int noteId) {
        return mapper.getNote(userId, noteId);
    }

    public List<Note> getNotes(String userId) {
        return mapper.getNotes(userId);
    }
}
