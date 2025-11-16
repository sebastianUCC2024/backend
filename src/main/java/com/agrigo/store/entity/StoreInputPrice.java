package com.agrigo.store.entity;

import com.agrigo.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "store_input_prices")
@Getter
@Setter
@NoArgsConstructor
public class StoreInputPrice extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
    
    @ManyToOne
    @JoinColumn(name = "input_id", nullable = false)
    private AgriculturalInput input;
    
    @NotNull
    @Column(nullable = false)
    private Double price;
    
    @NotNull
    private Integer stock;
    
    private Boolean available = true;
    
    @Builder
    public StoreInputPrice(Store store, AgriculturalInput input, Double price, 
                          Integer stock, Boolean available) {
        this.store = store;
        this.input = input;
        this.price = price;
        this.stock = stock;
        this.available = available != null ? available : true;
    }
}
