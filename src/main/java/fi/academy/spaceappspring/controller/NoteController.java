package fi.academy.spaceappspring.controller;

import fi.academy.spaceappspring.exception.ResourceNotFoundException;
import fi.academy.spaceappspring.model.Note;
import fi.academy.spaceappspring.model.User;
import fi.academy.spaceappspring.payload.ApiResponse;
import fi.academy.spaceappspring.payload.NoteRequest;
import fi.academy.spaceappspring.repository.NoteRepository;
import fi.academy.spaceappspring.repository.UserRepository;
import fi.academy.spaceappspring.security.CurrentUser;
import fi.academy.spaceappspring.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('USER')")
    public List<Note> getUserNotes(@PathVariable(value = "username") String username, @CurrentUser UserPrincipal currentUser) {
        List<Note> userNotes = new ArrayList<>();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        for(Note n : user.getNotes()) {
            userNotes.add(n);
        }
        return userNotes;

    }

    @PostMapping("/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addNote(@PathVariable(value = "username") String username, @RequestBody NoteRequest noteRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Note note = new Note(noteRequest.getText());
        noteRepository.save(note);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/{username}/{noteid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> modifyNote(@PathVariable(value = "username") String username,
                                        @PathVariable(value = "noteid") Long noteid,
                                        @RequestBody NoteRequest noteRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Optional<Note> note = noteRepository.findById(noteid);
        Note notev = note.get();
        notev.setText(noteRequest.getText());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{noteId}")
                .buildAndExpand(notev.getId()).toUri();
        noteRepository.save(notev);
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Note Created Successfully"));
    }

}
