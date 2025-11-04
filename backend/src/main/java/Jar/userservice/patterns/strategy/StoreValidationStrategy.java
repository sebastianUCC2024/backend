package Jar.userservice.patterns.strategy;

import Jar.userservice.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class StoreValidationStrategy implements ValidationStrategy {

    @Override
    public boolean validate(RegisterRequest request) {
        // Validaciones específicas para tiendas
        if (request.getLocation() == null || request.getLocation().isEmpty()) {
            return false;
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "Tienda must provide location and phone number";
    }
}
