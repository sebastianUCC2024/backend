package com.agrigo.ai.factory;

import com.agrigo.ai.entity.RecommendationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AIRequestFactoryProvider {
    
    private final FertilizerRequestFactory fertilizerRequestFactory;
    private final PesticideRequestFactory pesticideRequestFactory;
    private final OptimizationRequestFactory optimizationRequestFactory;
    
    public AIRequestFactory getFactory(RecommendationType type) {
        return switch (type) {
            case FERTILIZER -> fertilizerRequestFactory;
            case PESTICIDE -> pesticideRequestFactory;
            case OPTIMIZATION -> optimizationRequestFactory;
            default -> optimizationRequestFactory;
        };
    }
}
