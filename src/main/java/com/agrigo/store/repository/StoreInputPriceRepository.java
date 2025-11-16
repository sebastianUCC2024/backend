package com.agrigo.store.repository;

import com.agrigo.store.entity.StoreInputPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreInputPriceRepository extends JpaRepository<StoreInputPrice, Long> {
    List<StoreInputPrice> findByStoreId(Long storeId);
    List<StoreInputPrice> findByInputId(Long inputId);
    Optional<StoreInputPrice> findByStoreIdAndInputId(Long storeId, Long inputId);
}
