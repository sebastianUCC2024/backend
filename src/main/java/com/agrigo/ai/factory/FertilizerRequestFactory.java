package com.agrigo.ai.factory;

import com.agrigo.farmer.entity.Crop;
import org.springframework.stereotype.Component;

@Component
public class FertilizerRequestFactory implements AIRequestFactory {
    
    @Override
    public String createPrompt(Crop crop) {
        return String.format(
            "As an agricultural expert, recommend the best fertilizers for a %s crop. " +
            "Crop details: Type=%s, Area=%.2f hectares, Stage=%s, Soil=%s, Climate=%s. " +
            "Provide: 1) Recommended fertilizers, 2) Quantities needed, 3) Application timing, " +
            "4) Detailed explanation of why these fertilizers are suitable.",
            crop.getCropName(),
            crop.getCropType(),
            crop.getArea(),
            crop.getStage(),
            crop.getSoilType(),
            crop.getClimate()
        );
    }
}
