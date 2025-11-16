package com.agrigo.ai.dto;

import com.agrigo.ai.entity.RecommendationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecommendationRequest {
    @NotNull
    private Long cropId;
    
    @NotNull
    private RecommendationType type;
}
