package com.agrigo.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank
    private String productName;
    
    @NotBlank
    private String category;
    
    @NotNull
    private Double price;
    
    @NotNull
    private Double quantity;
    
    private String unit;
    private String description;
    private String quality;
}
