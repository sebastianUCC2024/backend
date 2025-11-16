package com.agrigo.marketplace.controller;

import com.agrigo.marketplace.dto.ProductRequest;
import com.agrigo.marketplace.dto.ProductResponse;
import com.agrigo.marketplace.service.MarketplaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marketplace")
@RequiredArgsConstructor
public class MarketplaceController {
    
    private final MarketplaceService marketplaceService;
    
    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(marketplaceService.createProduct(userId, request));
    }
    
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(marketplaceService.getAllProducts());
    }
    
    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(marketplaceService.getProductsByCategory(category));
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(marketplaceService.getProductById(id));
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(marketplaceService.updateProduct(id, request));
    }
    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        marketplaceService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
