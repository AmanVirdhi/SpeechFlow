
package com.speechflow.api.controller;

import com.speechflow.api.entity.Transcription;
import com.speechflow.api.service.TranscriptionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transcriptions")
public class TranscriptionController {

    private final TranscriptionService transcriptionService;

    // Constructor Injection
    public TranscriptionController(
            TranscriptionService transcriptionService) {

        this.transcriptionService = transcriptionService;
    }

    // CREATE - Save a new transcription
    @PostMapping
    public ResponseEntity<Transcription> createTranscription(
            @RequestBody Transcription transcription) {

        Transcription saved =
                transcriptionService.createTranscription(transcription);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ ALL - Get all transcriptions
    @GetMapping
    public ResponseEntity<List<Transcription>> getAllTranscriptions() {

        return ResponseEntity.ok(
                transcriptionService.getAllTranscriptions()
        );
    }

    // READ BY ID - Get a specific transcription
    @GetMapping("/{id}")
    public ResponseEntity<Transcription> getTranscriptionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                transcriptionService.getTranscriptionById(id)
        );
    }

    // UPDATE - Update an existing transcription
    @PutMapping("/{id}")
    public ResponseEntity<Transcription> updateTranscription(
            @PathVariable Long id,
            @RequestBody Transcription transcription) {

        Transcription updated =
                transcriptionService.updateTranscription(id, transcription);

        return ResponseEntity.ok(updated);
    }

    // DELETE - Delete a transcription
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranscription(
            @PathVariable Long id) {

        transcriptionService.deleteTranscription(id);

        return ResponseEntity.noContent().build();
    }
}