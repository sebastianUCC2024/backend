package com.agrigo.store.repository;

import com.agrigo.store.entity.AgriculturalInput;
import com.agrigo.store.entity.InputType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgriculturalInputRepository extends JpaRepository<AgriculturalInput, Long> {
    List<AgriculturalInput> findByType(InputType type);
    List<AgriculturalInput> findByNameContainingIgnoreCase(String name);
}
