package com.medilabo.note.controller;

import com.medilabo.note.model.Note;
import com.medilabo.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/patient/{patId}")
    public List<Note> getNotesByPatientId(@PathVariable Integer patId) {
        return noteService.getNotesByPatId(patId);
    }

    @PostMapping
    public Note addNote(@RequestBody Note note) {
        return noteService.saveNote(note);
    }
}
