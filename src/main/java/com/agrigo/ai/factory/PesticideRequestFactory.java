package com.agrigo.ai.factory;

import com.agrigo.farmer.entity.Crop;
import org.springframework.stereotype.Component;

@Component
public class PesticideRequestFactory implements AIRequestFactory {
    
    @Override
    public String createPrompt(Crop crop) {
        return String.format(
            "As an agricultural expert, recommend the best pesticides and pest control for a %s crop. " +
            "Crop details: Type=%s, Area=%.2f hectares, Stage=%s, Climate=%s. " +
            "Provide: 1) Recommended pesticides, 2) Quantities and dosage, 3) Application method, " +
            "4) Safety precautions, 5) Explanation of pest threats and prevention.",
            crop.getCropName(),
            crop.getCropType(),
            crop.getArea(),
            crop.getStage(),
            crop.getClimate()
        );
    }
}
