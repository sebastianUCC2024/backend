package Jar.userservice.patterns.strategy;


import Jar.userservice.dto.RegisterRequest;
import Jar.userservice.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationStrategyFactory {

    @Autowired
    private FarmerValidationStrategy farmerValidationStrategy;

    @Autowired
    private StoreValidationStrategy storeValidationStrategy;

    public ValidationStrategy getStrategy(Role role) {
        switch (role) {
            case FARMER:
                return farmerValidationStrategy;
            case STORE:
                return storeValidationStrategy;
            case CUSTOMER:
            case ADMINISTRATION:
            default:
                return new DefaultValidationStrategy();
        }
    }

    // Clase interna para validación por defecto
    private static class DefaultValidationStrategy implements ValidationStrategy {

        @Override
        public boolean validate(RegisterRequest request) {
            return true; // Por defecto, siempre válido
        }

        @Override
        public String getErrorMessage() {
            return "Validation passed";
        }
    }
}

