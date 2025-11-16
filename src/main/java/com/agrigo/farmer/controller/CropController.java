package com.agrigo.farmer.controller;

import com.agrigo.farmer.dto.CropRequest;
import com.agrigo.farmer.dto.CropResponse;
import com.agrigo.farmer.service.CropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farmers/crops")
@RequiredArgsConstructor
public class CropController {
    
    private final CropService cropService;
    
    @PostMapping
    public ResponseEntity<CropResponse> createCrop(
            @Valid @RequestBody CropRequest request,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cropService.createCrop(userId, request));
    }
    
    @GetMapping
    public ResponseEntity<List<CropResponse>> getMyCrops(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(cropService.getFarmerCrops(userId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CropResponse> getCrop(@PathVariable Long id) {
        return ResponseEntity.ok(cropService.getCropById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CropResponse> updateCrop(
            @PathVariable Long id,
            @Valid @RequestBody CropRequest request) {
        return ResponseEntity.ok(cropService.updateCrop(id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }
    
    private Long getUserIdFromAuth(Authentication authentication) {
        return 1L;
    }
}
