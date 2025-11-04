package Jar.userservice.patterns.strategy;

import Jar.userservice.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class FarmerValidationStrategy implements ValidationStrategy {

    @Override
    public boolean validate(RegisterRequest request) {
        // Validaciones específicas para agricultores
        if (request.getLocation() == null || request.getLocation().isEmpty()) {
            return false;
        }
        // Aquí podrían ir más validaciones específicas
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "Agricultor must provide location information";
    }
}
