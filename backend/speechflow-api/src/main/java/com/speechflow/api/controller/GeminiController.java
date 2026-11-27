
package com.speechflow.api.controller;

import com.speechflow.api.service.GeminiTokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiTokenService geminiTokenService;

    public GeminiController(GeminiTokenService geminiTokenService) {
        this.geminiTokenService = geminiTokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> generateToken() {

        Map<String, Object> response =
                geminiTokenService.generateToken();

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("token", response.get("name"));

        if (response.get("expireTime") != null) {
            result.put("expireTime", response.get("expireTime"));
        }

        if (response.get("newSessionExpireTime") != null) {
            result.put("newSessionExpireTime",
                    response.get("newSessionExpireTime"));
        }

        return ResponseEntity.ok(result);
    }
}