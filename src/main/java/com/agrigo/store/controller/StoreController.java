package com.agrigo.store.controller;

import com.agrigo.store.dto.InputPriceRequest;
import com.agrigo.store.dto.InputPriceResponse;
import com.agrigo.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    
    private final StoreService storeService;
    
    @PostMapping("/inputs")
    public ResponseEntity<InputPriceResponse> addInputPrice(
            @Valid @RequestBody InputPriceRequest request,
            Authentication authentication) {
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storeService.addInputPrice(userId, request));
    }
    
    @GetMapping("/inputs")
    public ResponseEntity<List<InputPriceResponse>> getMyInputs(Authentication authentication) {
        Long userId = 1L;
        return ResponseEntity.ok(storeService.getStoreInputs(userId));
    }
    
    @PutMapping("/inputs/{id}")
    public ResponseEntity<InputPriceResponse> updateInputPrice(
            @PathVariable Long id,
            @Valid @RequestBody InputPriceRequest request) {
        return ResponseEntity.ok(storeService.updateInputPrice(id, request));
    }
    
    @DeleteMapping("/inputs/{id}")
    public ResponseEntity<Void> deleteInputPrice(@PathVariable Long id) {
        storeService.deleteInputPrice(id);
        return ResponseEntity.noContent().build();
    }
}
