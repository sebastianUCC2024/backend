package com.agrigo.store.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InputPriceRequest {
    @NotNull
    private Long inputId;
    
    @NotNull
    private Double price;
    
    @NotNull
    private Integer stock;
    
    private Boolean available;
}
