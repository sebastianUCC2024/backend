package com.agrigo.pricecomparator.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PriceComparisonResponse {
    private Long inputId;
    private String inputName;
    private String inputType;
    private Double minPrice;
    private Double maxPrice;
    private Double avgPrice;
    private String bestStoreName;
    private Long bestStoreId;
    private Integer totalStores;
    private List<StorePrice> storePrices;
    
    @Data
    @Builder
    public static class StorePrice {
        private Long storeId;
        private String storeName;
        private Double price;
        private Integer stock;
        private Boolean available;
    }
}
