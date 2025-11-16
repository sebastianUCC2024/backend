package com.agrigo.ai.factory;

import com.agrigo.farmer.entity.Crop;
import org.springframework.stereotype.Component;

@Component
public class OptimizationRequestFactory implements AIRequestFactory {
    
    @Override
    public String createPrompt(Crop crop) {
        return String.format(
            "As an agricultural expert, provide a complete optimization plan for a %s crop. " +
            "Crop details: Type=%s, Area=%.2f hectares, Stage=%s, Soil=%s, Climate=%s. " +
            "Provide: 1) Optimal fertilizer combination, 2) Pesticide strategy, " +
            "3) Cost-effective input quantities, 4) Expected yield improvement, " +
            "5) Detailed explanation of the optimization strategy.",
            crop.getCropName(),
            crop.getCropType(),
            crop.getArea(),
            crop.getStage(),
            crop.getSoilType(),
            crop.getClimate()
        );
    }
}
