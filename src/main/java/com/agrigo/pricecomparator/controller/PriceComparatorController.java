package com.agrigo.pricecomparator.controller;

import com.agrigo.pricecomparator.dto.PriceComparisonResponse;
import com.agrigo.pricecomparator.service.PriceComparatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/price-comparator")
@RequiredArgsConstructor
public class PriceComparatorController {
    
    private final PriceComparatorService priceComparatorService;
    
    @GetMapping("/compare/{inputId}")
    public ResponseEntity<PriceComparisonResponse> compareInputPrices(@PathVariable Long inputId) {
        return ResponseEntity.ok(priceComparatorService.compareInputPrices(inputId));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PriceComparisonResponse>> getAllComparisons() {
        return ResponseEntity.ok(priceComparatorService.getAllComparisons());
    }
}
