package com.agrigo.pricecomparator.service;

import com.agrigo.common.exception.ResourceNotFoundException;
import com.agrigo.pricecomparator.dto.PriceComparisonResponse;
import com.agrigo.pricecomparator.entity.PriceComparison;
import com.agrigo.pricecomparator.repository.PriceComparisonRepository;
import com.agrigo.store.entity.AgriculturalInput;
import com.agrigo.store.entity.StoreInputPrice;
import com.agrigo.store.repository.AgriculturalInputRepository;
import com.agrigo.store.repository.StoreInputPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceComparatorService {
    
    private final StoreInputPriceRepository storeInputPriceRepository;
    private final AgriculturalInputRepository agriculturalInputRepository;
    private final PriceComparisonRepository priceComparisonRepository;
    
    @Transactional
    public PriceComparisonResponse compareInputPrices(Long inputId) {
        AgriculturalInput input = agriculturalInputRepository.findById(inputId)
                .orElseThrow(() -> new ResourceNotFoundException("Input not found"));
        
        List<StoreInputPrice> prices = storeInputPriceRepository.findByInputId(inputId);
        
        if (prices.isEmpty()) {
            throw new ResourceNotFoundException("No prices found for this input");
        }
        
        List<StoreInputPrice> availablePrices = prices.stream()
                .filter(StoreInputPrice::getAvailable)
                .collect(Collectors.toList());
        
        if (availablePrices.isEmpty()) {
            throw new ResourceNotFoundException("No available prices for this input");
        }
        
        Double minPrice = availablePrices.stream()
                .map(StoreInputPrice::getPrice)
                .min(Double::compare)
                .orElse(0.0);
        
        Double maxPrice = availablePrices.stream()
                .map(StoreInputPrice::getPrice)
                .max(Double::compare)
                .orElse(0.0);
        
        Double avgPrice = availablePrices.stream()
                .map(StoreInputPrice::getPrice)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        
        StoreInputPrice bestPrice = availablePrices.stream()
                .min(Comparator.comparing(StoreInputPrice::getPrice))
                .orElse(null);
        
        PriceComparison comparison = PriceComparison.builder()
                .input(input)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .avgPrice(avgPrice)
                .bestStoreId(bestPrice != null ? bestPrice.getStore().getId() : null)
                .bestStoreName(bestPrice != null ? bestPrice.getStore().getStoreName() : null)
                .totalStores(availablePrices.size())
                .build();
        
        priceComparisonRepository.save(comparison);
        
        List<PriceComparisonResponse.StorePrice> storePrices = availablePrices.stream()
                .map(sip -> PriceComparisonResponse.StorePrice.builder()
                        .storeId(sip.getStore().getId())
                        .storeName(sip.getStore().getStoreName())
                        .price(sip.getPrice())
                        .stock(sip.getStock())
                        .available(sip.getAvailable())
                        .build())
                .collect(Collectors.toList());
        
        return PriceComparisonResponse.builder()
                .inputId(input.getId())
                .inputName(input.getName())
                .inputType(input.getType().name())
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .avgPrice(avgPrice)
                .bestStoreName(bestPrice != null ? bestPrice.getStore().getStoreName() : null)
                .bestStoreId(bestPrice != null ? bestPrice.getStore().getId() : null)
                .totalStores(availablePrices.size())
                .storePrices(storePrices)
                .build();
    }
    
    public List<PriceComparisonResponse> getAllComparisons() {
        List<AgriculturalInput> inputs = agriculturalInputRepository.findAll();
        
        return inputs.stream()
                .filter(input -> !storeInputPriceRepository.findByInputId(input.getId()).isEmpty())
                .map(input -> compareInputPrices(input.getId()))
                .collect(Collectors.toList());
    }
}
