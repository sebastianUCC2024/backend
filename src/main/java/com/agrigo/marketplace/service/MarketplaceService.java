package com.agrigo.marketplace.service;

import com.agrigo.common.exception.ResourceNotFoundException;
import com.agrigo.farmer.entity.Farmer;
import com.agrigo.farmer.repository.FarmerRepository;
import com.agrigo.marketplace.dto.ProductRequest;
import com.agrigo.marketplace.dto.ProductResponse;
import com.agrigo.marketplace.entity.FarmerProduct;
import com.agrigo.marketplace.entity.ProductStatus;
import com.agrigo.marketplace.repository.FarmerProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketplaceService {
    
    private final FarmerProductRepository farmerProductRepository;
    private final FarmerRepository farmerRepository;
    
    @Transactional
    public ProductResponse createProduct(Long userId, ProductRequest request) {
        Farmer farmer = farmerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        
        FarmerProduct product = FarmerProduct.builder()
                .farmer(farmer)
                .productName(request.getProductName())
                .category(request.getCategory())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .unit(request.getUnit())
                .description(request.getDescription())
                .quality(request.getQuality())
                .status(ProductStatus.AVAILABLE)
                .build();
        
        product = farmerProductRepository.save(product);
        return mapToResponse(product);
    }
    
    public List<ProductResponse> getAllProducts() {
        return farmerProductRepository.findByStatus(ProductStatus.AVAILABLE).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<ProductResponse> getProductsByCategory(String category) {
        return farmerProductRepository.findByCategory(category).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public ProductResponse getProductById(Long productId) {
        FarmerProduct product = farmerProductRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return mapToResponse(product);
    }
    
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        FarmerProduct product = farmerProductRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        product.setProductName(request.getProductName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setUnit(request.getUnit());
        product.setDescription(request.getDescription());
        product.setQuality(request.getQuality());
        
        product = farmerProductRepository.save(product);
        return mapToResponse(product);
    }
    
    @Transactional
    public void deleteProduct(Long productId) {
        FarmerProduct product = farmerProductRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        farmerProductRepository.delete(product);
    }
    
    private ProductResponse mapToResponse(FarmerProduct product) {
        return ProductResponse.builder()
                .id(product.getId())
                .farmerId(product.getFarmer().getId())
                .farmerName(product.getFarmer().getUser().getFullName())
                .productName(product.getProductName())
                .category(product.getCategory())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .unit(product.getUnit())
                .description(product.getDescription())
                .quality(product.getQuality())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
