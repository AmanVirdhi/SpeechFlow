
package com.speechflow.api.service;

import com.speechflow.api.entity.Transcription;
import com.speechflow.api.repository.TranscriptionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class TranscriptionService {

    private final TranscriptionRepository transcriptionRepository;

    // Constructor Injection
    public TranscriptionService(
            TranscriptionRepository transcriptionRepository) {

        this.transcriptionRepository = transcriptionRepository;
    }

    // CREATE
    public Transcription createTranscription(
            Transcription transcription) {

        return transcriptionRepository.save(transcription);
    }

    // READ ALL
    @Transactional(readOnly = true)
    public List<Transcription> getAllTranscriptions() {

        return transcriptionRepository.findAll();
    }

    // READ BY ID
    @Transactional(readOnly = true)
    public Transcription getTranscriptionById(Long id) {

        return transcriptionRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Transcription not found with id: " + id
                    )
                );
    }

    // UPDATE
    public Transcription updateTranscription(
            Long id, Transcription updatedData) {

        Transcription existing = getTranscriptionById(id);

        existing.setTitle(updatedData.getTitle());
        existing.setContent(updatedData.getContent());
        existing.setLanguage(updatedData.getLanguage());
        existing.setDuration(updatedData.getDuration());

        return transcriptionRepository.save(existing);
    }

    // DELETE
    public void deleteTranscription(Long id) {

        Transcription existing = getTranscriptionById(id);

        transcriptionRepository.delete(existing);
    }
}
