package com.agrigo.common.config;

import com.agrigo.auth.entity.User;
import com.agrigo.auth.entity.UserRole;
import com.agrigo.auth.repository.UserRepository;
import com.agrigo.farmer.entity.Farmer;
import com.agrigo.farmer.repository.FarmerRepository;
import com.agrigo.store.entity.AgriculturalInput;
import com.agrigo.store.entity.InputType;
import com.agrigo.store.entity.Store;
import com.agrigo.store.repository.AgriculturalInputRepository;
import com.agrigo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final FarmerRepository farmerRepository;
    private final StoreRepository storeRepository;
    private final AgriculturalInputRepository agriculturalInputRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            loadInitialData();
        }
    }
    
    private void loadInitialData() {
        User farmerUser = User.builder()
                .username("farmer1")
                .email("farmer1@agrigo.com")
                .password(passwordEncoder.encode("password123"))
                .role(UserRole.FARMER)
                .fullName("Juan Pérez")
                .phone("555-1234")
                .address("Finca El Paraíso")
                .active(true)
                .build();
        farmerUser = userRepository.save(farmerUser);
        
        Farmer farmer = Farmer.builder()
                .user(farmerUser)
                .farmName("Finca El Paraíso")
                .farmArea(50.0)
                .location("Valle del Cauca")
                .farmType("Mixed")
                .build();
        farmerRepository.save(farmer);
        
        User storeUser = User.builder()
                .username("store1")
                .email("store1@agrigo.com")
                .password(passwordEncoder.encode("password123"))
                .role(UserRole.STORE)
                .fullName("Agrotienda Central")
                .phone("555-5678")
                .address("Calle Principal 123")
                .active(true)
                .build();
        storeUser = userRepository.save(storeUser);
        
        Store store = Store.builder()
                .user(storeUser)
                .storeName("Agrotienda Central")
                .location("Cali")
                .phone("555-5678")
                .description("Insumos agrícolas de calidad")
                .verified(true)
                .build();
        storeRepository.save(store);
        
        User adminUser = User.builder()
                .username("admin")
                .email("admin@agrigo.com")
                .password(passwordEncoder.encode("admin123"))
                .role(UserRole.ADMIN)
                .fullName("Administrator")
                .active(true)
                .build();
        userRepository.save(adminUser);
        
        AgriculturalInput fertilizer1 = AgriculturalInput.builder()
                .name("Urea 46%")
                .type(InputType.FERTILIZER)
                .category("Nitrogen Fertilizer")
                .brand("AgroMax")
                .composition("46% Nitrogen")
                .description("High quality nitrogen fertilizer")
                .unit("kg")
                .build();
        agriculturalInputRepository.save(fertilizer1);
        
        AgriculturalInput fertilizer2 = AgriculturalInput.builder()
                .name("NPK 15-15-15")
                .type(InputType.FERTILIZER)
                .category("Complete Fertilizer")
                .brand("FertiCrop")
                .composition("15% N, 15% P, 15% K")
                .description("Balanced complete fertilizer")
                .unit("kg")
                .build();
        agriculturalInputRepository.save(fertilizer2);
        
        AgriculturalInput pesticide1 = AgriculturalInput.builder()
                .name("Glyphosate 48%")
                .type(InputType.PESTICIDE)
                .category("Herbicide")
                .brand("CropGuard")
                .composition("48% Glyphosate")
                .description("Broad spectrum herbicide")
                .unit("liter")
                .build();
        agriculturalInputRepository.save(pesticide1);
    }
}
