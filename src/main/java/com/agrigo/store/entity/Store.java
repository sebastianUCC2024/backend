package com.agrigo.store.entity;

import com.agrigo.auth.entity.User;
import com.agrigo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store extends BaseEntity {
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(nullable = false)
    private String storeName;
    
    private String location;
    private String phone;
    private String description;
    private Boolean verified = false;
}
