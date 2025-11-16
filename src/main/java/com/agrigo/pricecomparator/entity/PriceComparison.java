package com.agrigo.pricecomparator.entity;

import com.agrigo.common.entity.BaseEntity;
import com.agrigo.store.entity.AgriculturalInput;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "price_comparisons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceComparison extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "input_id", nullable = false)
    private AgriculturalInput input;
    
    private Double minPrice;
    private Double maxPrice;
    private Double avgPrice;
    
    private Long bestStoreId;
    private String bestStoreName;
    
    private Integer totalStores;
}
