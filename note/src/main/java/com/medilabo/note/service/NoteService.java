package com.medilabo.note.service;

import com.medilabo.note.model.Note;
import com.medilabo.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getNotesByPatId(Integer patId) {
        return noteRepository.findByPatId(patId);
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }
}
