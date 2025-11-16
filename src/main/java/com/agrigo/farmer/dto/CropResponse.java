package com.agrigo.farmer.dto;

import com.agrigo.farmer.entity.CropStage;
import com.agrigo.farmer.entity.CropStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CropResponse {
    private Long id;
    private Long farmerId;
    private String cropName;
    private String cropType;
    private Double area;
    private LocalDate plantingDate;
    private CropStage stage;
    private String soilType;
    private String climate;
    private CropStatus status;
    private LocalDateTime createdAt;
}
