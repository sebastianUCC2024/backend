package com.agrigo.marketplace.repository;

import com.agrigo.marketplace.entity.FarmerProduct;
import com.agrigo.marketplace.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerProductRepository extends JpaRepository<FarmerProduct, Long> {
    List<FarmerProduct> findByFarmerId(Long farmerId);
    List<FarmerProduct> findByStatus(ProductStatus status);
    List<FarmerProduct> findByCategory(String category);
}
