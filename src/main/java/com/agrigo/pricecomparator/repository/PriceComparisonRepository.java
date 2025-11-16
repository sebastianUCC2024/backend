package com.agrigo.pricecomparator.repository;

import com.agrigo.pricecomparator.entity.PriceComparison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceComparisonRepository extends JpaRepository<PriceComparison, Long> {
    Optional<PriceComparison> findByInputId(Long inputId);
}
