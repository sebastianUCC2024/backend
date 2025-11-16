package com.agrigo.ai.dto;

import com.agrigo.ai.entity.RecommendationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RecommendationResponse {
    private Long id;
    private Long cropId;
    private RecommendationType type;
    private String fertilizers;
    private String pesticides;
    private String quantities;
    private String explanation;
    private LocalDateTime createdAt;
}
