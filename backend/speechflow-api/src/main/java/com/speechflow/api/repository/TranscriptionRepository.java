
package com.speechflow.api.repository;

import com.speechflow.api.entity.Transcription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranscriptionRepository
        extends JpaRepository<Transcription, Long> {

}