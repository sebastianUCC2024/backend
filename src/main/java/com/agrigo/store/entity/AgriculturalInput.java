package com.agrigo.store.entity;

import com.agrigo.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "agricultural_inputs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgriculturalInput extends BaseEntity {
    
    @NotBlank
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InputType type;
    
    private String category;
    private String brand;
    private String composition;
    private String description;
    private String unit;
}
