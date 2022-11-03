package com.zerobase.fitme.service;

import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.model.RegCategory.Request;
import com.zerobase.fitme.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService{
    private final CategoryRepository categoryRepository;
    public void register(Request request) {
        Category category = new Category();
        category.setName(request.getCategoryName());
        category.setUsingYn(false);// 등록을 안했으니 false

        categoryRepository.save(category);
    }
}
