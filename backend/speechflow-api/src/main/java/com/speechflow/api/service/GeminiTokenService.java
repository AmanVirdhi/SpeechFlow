
package com.speechflow.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class GeminiTokenService {

    private static final Logger log =
            LoggerFactory.getLogger(GeminiTokenService.class);

    private final RestClient restClient;

    public GeminiTokenService(
            @Value("${gemini.api.key}") String apiKey) {

        this.restClient = RestClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .defaultHeader("x-goog-api-key", apiKey)
                .build();
    }

    public Map<String, Object> generateToken() {

        Instant now = Instant.now();

        Map<String, Object> requestBody = Map.of(
                "uses", 1,
                "expireTime",
                now.plus(30, ChronoUnit.MINUTES).toString(),
                "newSessionExpireTime",
                now.plus(1, ChronoUnit.MINUTES).toString()
        );

        try {

            Map<String, Object> response =
                    restClient.post()
                            .uri("/v1alpha/auth_tokens")
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(requestBody)
                            .retrieve()
                            .body(new ParameterizedTypeReference<
                                    Map<String, Object>>() {});

            if (response == null || response.get("name") == null) {
                throw new IllegalStateException(
                        "Gemini returned an empty token response");
            }

            log.info("Gemini ephemeral token generated successfully");

            return response;

        } catch (RestClientResponseException ex) {

            log.error("Gemini API Error: Status={}, Response={}",
                    ex.getStatusCode(),
                    ex.getResponseBodyAsString());

            throw ex;
        }
    }
}