package com.agrigo.store.service;

import com.agrigo.common.exception.ResourceNotFoundException;
import com.agrigo.store.dto.InputPriceRequest;
import com.agrigo.store.dto.InputPriceResponse;
import com.agrigo.store.entity.AgriculturalInput;
import com.agrigo.store.entity.Store;
import com.agrigo.store.entity.StoreInputPrice;
import com.agrigo.store.repository.AgriculturalInputRepository;
import com.agrigo.store.repository.StoreInputPriceRepository;
import com.agrigo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    
    private final StoreRepository storeRepository;
    private final StoreInputPriceRepository storeInputPriceRepository;
    private final AgriculturalInputRepository agriculturalInputRepository;
    
    @Transactional
    public InputPriceResponse addInputPrice(Long userId, InputPriceRequest request) {
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        
        AgriculturalInput input = agriculturalInputRepository.findById(request.getInputId())
                .orElseThrow(() -> new ResourceNotFoundException("Input not found"));
        
        StoreInputPrice storeInputPrice = StoreInputPrice.builder()
                .store(store)
                .input(input)
                .price(request.getPrice())
                .stock(request.getStock())
                .available(request.getAvailable() != null ? request.getAvailable() : true)
                .build();
        
        storeInputPrice = storeInputPriceRepository.save(storeInputPrice);
        return mapToResponse(storeInputPrice);
    }
    
    public List<InputPriceResponse> getStoreInputs(Long userId) {
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        
        return storeInputPriceRepository.findByStoreId(store.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public InputPriceResponse updateInputPrice(Long priceId, InputPriceRequest request) {
        StoreInputPrice storeInputPrice = storeInputPriceRepository.findById(priceId)
                .orElseThrow(() -> new ResourceNotFoundException("Input price not found"));
        
        storeInputPrice.setPrice(request.getPrice());
        storeInputPrice.setStock(request.getStock());
        storeInputPrice.setAvailable(request.getAvailable());
        
        storeInputPrice = storeInputPriceRepository.save(storeInputPrice);
        return mapToResponse(storeInputPrice);
    }
    
    @Transactional
    public void deleteInputPrice(Long priceId) {
        StoreInputPrice storeInputPrice = storeInputPriceRepository.findById(priceId)
                .orElseThrow(() -> new ResourceNotFoundException("Input price not found"));
        storeInputPriceRepository.delete(storeInputPrice);
    }
    
    private InputPriceResponse mapToResponse(StoreInputPrice sip) {
        return InputPriceResponse.builder()
                .id(sip.getId())
                .storeId(sip.getStore().getId())
                .storeName(sip.getStore().getStoreName())
                .inputId(sip.getInput().getId())
                .inputName(sip.getInput().getName())
                .inputType(sip.getInput().getType().name())
                .price(sip.getPrice())
                .stock(sip.getStock())
                .available(sip.getAvailable())
                .build();
    }
}
