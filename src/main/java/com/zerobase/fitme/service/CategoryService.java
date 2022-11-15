package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.CategoryErrorCode.ALREADY_EXIST_CATEGORY_NAME;
import static com.zerobase.fitme.exception.type.CategoryErrorCode.CATEGORY_NOT_FOUND;
import static com.zerobase.fitme.exception.type.CategoryErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.exception.CategoryException;
import com.zerobase.fitme.model.RegCategory.Request;
import com.zerobase.fitme.model.UdtCategory;
import com.zerobase.fitme.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService{
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 등록
     * @param request
     */
    public void register(Request request) {
        if(categoryRepository.findByCategoryName(request.getCategoryName()).isPresent()){
            throw new CategoryException(ALREADY_EXIST_CATEGORY_NAME);
        }

        categoryRepository.save(Category.builder()
                .categoryName(request.getCategoryName())
                .using(false)// 아직 등록 안함
                .build());
        }

    /**
     * 카테고리 수정
     * @param request
     */
    public void patch(UdtCategory.Request request) {
        validationPatch(request);

        Category category = categoryRepository.findById(request.getId()).orElseThrow(() ->
            new CategoryException(CATEGORY_NOT_FOUND)
        );

        category.patch(request);

        categoryRepository.save(category);
    }

    /**
     * 카테고리 조회
     * @return
     */
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    private void validationPatch(UdtCategory.Request request) {
        if(!StringUtils.hasText(request.getCategoryName()) && request.getUsingYn() == null){
            throw new CategoryException(INVALID_REQUEST);
        }
        if(categoryRepository.findByCategoryName(request.getCategoryName()).isPresent()){
            throw new CategoryException(ALREADY_EXIST_CATEGORY_NAME);
        }
    }
}
