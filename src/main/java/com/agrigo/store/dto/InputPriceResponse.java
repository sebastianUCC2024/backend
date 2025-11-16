package com.agrigo.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputPriceResponse {
    private Long id;
    private Long storeId;
    private String storeName;
    private Long inputId;
    private String inputName;
    private String inputType;
    private Double price;
    private Integer stock;
    private Boolean available;
}
