package com.zerobase.fitme.service;

import com.zerobase.fitme.dto.ItemDto;
import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.ItemCategory;
import com.zerobase.fitme.repository.ItemCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemCategoryService {
    private final ItemCategoryRepository itemCategoryRepository;

    /**
     * 상품-카테고리 등록
     */
    public List<ItemCategory> register(Item item, List<Category> categoryList) {
        List<ItemCategory> itemCategoryList = new ArrayList<>();
        for(Category category: categoryList){
            itemCategoryList.add(itemCategoryRepository.save(ItemCategory.builder()
                    .item(item)
                    .category(category)
                    .build()));
        }

        return itemCategoryList;
    }

    public Page<ItemCategory> readByCategoryName(String categoryName, Pageable pageable) {
        return itemCategoryRepository.findByCategory_CategoryNameOrderByItemIdDesc(categoryName, pageable);
    }
}
