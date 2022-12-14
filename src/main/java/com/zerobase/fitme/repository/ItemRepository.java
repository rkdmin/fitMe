package com.zerobase.fitme.repository;

import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.Model;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByItemName(String itemName);
    List<Item> findTop20ByOrderByViewDesc();
    Page<Item> findByBrand_BrandNameOrderByIdDesc(String brandName, Pageable pageable);
}
