package com.agrigo.marketplace.dto;

import com.agrigo.marketplace.entity.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private Long farmerId;
    private String farmerName;
    private String productName;
    private String category;
    private Double price;
    private Double quantity;
    private String unit;
    private String description;
    private String quality;
    private ProductStatus status;
    private LocalDateTime createdAt;
}
