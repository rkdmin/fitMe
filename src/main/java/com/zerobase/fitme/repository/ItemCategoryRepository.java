package com.zerobase.fitme.repository;

import com.zerobase.fitme.entity.ItemCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    Page<ItemCategory> findByCategory_CategoryNameOrderByItemIdDesc(String categoryName, Pageable pageable);
}
