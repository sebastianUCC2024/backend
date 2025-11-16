package com.agrigo.farmer.service;

import com.agrigo.common.exception.ResourceNotFoundException;
import com.agrigo.farmer.dto.CropRequest;
import com.agrigo.farmer.dto.CropResponse;
import com.agrigo.farmer.entity.Crop;
import com.agrigo.farmer.entity.CropStatus;
import com.agrigo.farmer.entity.Farmer;
import com.agrigo.farmer.repository.CropRepository;
import com.agrigo.farmer.repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropService {
    
    private final CropRepository cropRepository;
    private final FarmerRepository farmerRepository;
    
    @Transactional
    public CropResponse createCrop(Long userId, CropRequest request) {
        Farmer farmer = farmerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        
        Crop crop = Crop.builder()
                .farmer(farmer)
                .cropName(request.getCropName())
                .cropType(request.getCropType())
                .area(request.getArea())
                .plantingDate(request.getPlantingDate())
                .stage(request.getStage())
                .soilType(request.getSoilType())
                .climate(request.getClimate())
                .status(CropStatus.ACTIVE)
                .build();
        
        crop = cropRepository.save(crop);
        return mapToResponse(crop);
    }
    
    public List<CropResponse> getFarmerCrops(Long userId) {
        Farmer farmer = farmerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        
        return cropRepository.findByFarmerId(farmer.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public CropResponse getCropById(Long cropId) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found"));
        return mapToResponse(crop);
    }
    
    @Transactional
    public CropResponse updateCrop(Long cropId, CropRequest request) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found"));
        
        crop.setCropName(request.getCropName());
        crop.setCropType(request.getCropType());
        crop.setArea(request.getArea());
        crop.setPlantingDate(request.getPlantingDate());
        crop.setStage(request.getStage());
        crop.setSoilType(request.getSoilType());
        crop.setClimate(request.getClimate());
        
        crop = cropRepository.save(crop);
        return mapToResponse(crop);
    }
    
    @Transactional
    public void deleteCrop(Long cropId) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found"));
        cropRepository.delete(crop);
    }
    
    private CropResponse mapToResponse(Crop crop) {
        return CropResponse.builder()
                .id(crop.getId())
                .farmerId(crop.getFarmer().getId())
                .cropName(crop.getCropName())
                .cropType(crop.getCropType())
                .area(crop.getArea())
                .plantingDate(crop.getPlantingDate())
                .stage(crop.getStage())
                .soilType(crop.getSoilType())
                .climate(crop.getClimate())
                .status(crop.getStatus())
                .createdAt(crop.getCreatedAt())
                .build();
    }
}
