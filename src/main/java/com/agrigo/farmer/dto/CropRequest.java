package com.agrigo.farmer.dto;

import com.agrigo.farmer.entity.CropStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CropRequest {
    @NotBlank
    private String cropName;
    
    @NotBlank
    private String cropType;
    
    @NotNull
    private Double area;
    
    private LocalDate plantingDate;
    private CropStage stage;
    private String soilType;
    private String climate;
}
