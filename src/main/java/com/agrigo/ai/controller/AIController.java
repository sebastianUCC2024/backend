package com.agrigo.ai.controller;

import com.agrigo.ai.dto.RecommendationRequest;
import com.agrigo.ai.dto.RecommendationResponse;
import com.agrigo.ai.service.AIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {
    
    private final AIService aiService;
    
    @PostMapping("/recommend")
    public ResponseEntity<RecommendationResponse> generateRecommendation(
            @Valid @RequestBody RecommendationRequest request) {
        return ResponseEntity.ok(aiService.generateRecommendation(request));
    }
    
    @GetMapping("/explain/{cropId}")
    public ResponseEntity<RecommendationResponse> getExplanation(@PathVariable Long cropId) {
        return ResponseEntity.ok(aiService.getExplanation(cropId));
    }
    
    @GetMapping("/recommendations/{cropId}")
    public ResponseEntity<List<RecommendationResponse>> getRecommendations(@PathVariable Long cropId) {
        return ResponseEntity.ok(aiService.getRecommendationsByCrop(cropId));
    }
}
