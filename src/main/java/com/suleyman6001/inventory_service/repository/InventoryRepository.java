package com.suleyman6001.inventory_service.repository;

import com.suleyman6001.inventory_service.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Integer> {
    Optional<InventoryItem> findByProductCode(String productCode);
    List<InventoryItem> findAllByProductName(String productName);
    Boolean existsByProductCode(String productCode);

    @Modifying
    @Query("""
    update InventoryItem i
    set i.availableQuantity = i.availableQuantity - :requestedQuantity
    where i.productCode = :productCode
    and i.availableQuantity >= :requestedQuantity
""")
    int reserveStock(String productCode, Integer requestedQuantity);

}
