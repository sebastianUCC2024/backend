package com.agrigo.farmer.entity;

import com.agrigo.auth.entity.User;
import com.agrigo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "farmers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farmer extends BaseEntity {
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    private String farmName;
    private Double farmArea;
    private String location;
    private String farmType;
}
