package com.agrigo.ai.service;

import com.agrigo.ai.adapter.AIAdapter;
import com.agrigo.ai.dto.RecommendationRequest;
import com.agrigo.ai.dto.RecommendationResponse;
import com.agrigo.ai.entity.Recommendation;
import com.agrigo.ai.entity.RecommendationType;
import com.agrigo.ai.factory.AIRequestFactory;
import com.agrigo.ai.factory.AIRequestFactoryProvider;
import com.agrigo.ai.repository.RecommendationRepository;
import com.agrigo.common.exception.ResourceNotFoundException;
import com.agrigo.farmer.entity.Crop;
import com.agrigo.farmer.repository.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIService {
    
    private final AIAdapter aiAdapter;
    private final AIRequestFactoryProvider factoryProvider;
    private final RecommendationRepository recommendationRepository;
    private final CropRepository cropRepository;
    
    @Transactional
    public RecommendationResponse generateRecommendation(RecommendationRequest request) {
        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found"));
        
        AIRequestFactory factory = factoryProvider.getFactory(request.getType());
        String prompt = factory.createPrompt(crop);
        
        String aiResponse = aiAdapter.generateRecommendation(prompt);
        
        Recommendation recommendation = Recommendation.builder()
                .crop(crop)
                .type(request.getType())
                .rawResponse(aiResponse)
                .explanation(extractExplanation(aiResponse))
                .fertilizers(extractFertilizers(aiResponse))
                .pesticides(extractPesticides(aiResponse))
                .quantities(extractQuantities(aiResponse))
                .build();
        
        recommendation = recommendationRepository.save(recommendation);
        
        return mapToResponse(recommendation);
    }
    
    public List<RecommendationResponse> getRecommendationsByCrop(Long cropId) {
        return recommendationRepository.findByCropId(cropId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public RecommendationResponse getExplanation(Long cropId) {
        List<Recommendation> recommendations = recommendationRepository.findByCropId(cropId);
        if (recommendations.isEmpty()) {
            throw new ResourceNotFoundException("No recommendations found for this crop");
        }
        return mapToResponse(recommendations.get(0));
    }
    
    private String extractExplanation(String response) {
        return response;
    }
    
    private String extractFertilizers(String response) {
        return response.contains("fertilizer") ? response : "See full recommendation";
    }
    
    private String extractPesticides(String response) {
        return response.contains("pesticide") ? response : "See full recommendation";
    }
    
    private String extractQuantities(String response) {
        return response.contains("kg") || response.contains("liter") ? response : "See full recommendation";
    }
    
    private RecommendationResponse mapToResponse(Recommendation rec) {
        return RecommendationResponse.builder()
                .id(rec.getId())
                .cropId(rec.getCrop().getId())
                .type(rec.getType())
                .fertilizers(rec.getFertilizers())
                .pesticides(rec.getPesticides())
                .quantities(rec.getQuantities())
                .explanation(rec.getExplanation())
                .createdAt(rec.getCreatedAt())
                .build();
    }
}
