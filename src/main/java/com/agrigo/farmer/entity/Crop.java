package com.agrigo.farmer.entity;

import com.agrigo.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "crops")
@Getter
@Setter
@NoArgsConstructor
public class Crop extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;
    
    @NotBlank
    @Column(nullable = false)
    private String cropName;
    
    @NotBlank
    private String cropType;
    
    @NotNull
    private Double area;
    
    private LocalDate plantingDate;
    
    @Enumerated(EnumType.STRING)
    private CropStage stage;
    
    private String soilType;
    private String climate;
    
    @Enumerated(EnumType.STRING)
    private CropStatus status;
    
    @Builder
    public Crop(Farmer farmer, String cropName, String cropType, Double area, 
                LocalDate plantingDate, CropStage stage, String soilType, 
                String climate, CropStatus status) {
        this.farmer = farmer;
        this.cropName = cropName;
        this.cropType = cropType;
        this.area = area;
        this.plantingDate = plantingDate;
        this.stage = stage;
        this.soilType = soilType;
        this.climate = climate;
        this.status = status != null ? status : CropStatus.ACTIVE;
    }
}
