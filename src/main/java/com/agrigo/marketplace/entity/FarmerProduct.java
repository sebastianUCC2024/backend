package com.agrigo.marketplace.entity;

import com.agrigo.common.entity.BaseEntity;
import com.agrigo.farmer.entity.Farmer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "farmer_products")
@Getter
@Setter
@NoArgsConstructor
public class FarmerProduct extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;
    
    @NotBlank
    @Column(nullable = false)
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
    
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    
    @Builder
    public FarmerProduct(Farmer farmer, String productName, String category, 
                        Double price, Double quantity, String unit, 
                        String description, String quality, ProductStatus status) {
        this.farmer = farmer;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.description = description;
        this.quality = quality;
        this.status = status != null ? status : ProductStatus.AVAILABLE;
    }
}
