package com.zerobase.fitme.service.category;

import static com.zerobase.fitme.exception.type.CategoryErrorCode.*;
import static com.zerobase.fitme.type.ColorType.GRAY;
import static com.zerobase.fitme.type.ColorType.RED;
import static com.zerobase.fitme.type.ColorType.YELLOW;
import static com.zerobase.fitme.type.SizeType.FREE;
import static com.zerobase.fitme.type.SizeType.L;
import static com.zerobase.fitme.type.SizeType.XL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.dto.CartDto;
import com.zerobase.fitme.dto.CategoryDto;
import com.zerobase.fitme.entity.Cart;
import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.ItemInfo;
import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.exception.CartException;
import com.zerobase.fitme.exception.CategoryException;
import com.zerobase.fitme.exception.type.CartErrorCode;
import com.zerobase.fitme.exception.type.CategoryErrorCode;
import com.zerobase.fitme.repository.CategoryRepository;
import com.zerobase.fitme.service.CartService;
import com.zerobase.fitme.service.CategoryService;
import com.zerobase.fitme.type.ColorType;
import com.zerobase.fitme.type.SizeType;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void 카테고리_등록_실패_이미존재하는카테고리명() {
        // given
        String categoryName = "스트릿";
        CategoryDto.Request request = CategoryDto.Request.builder()
            .categoryName(categoryName)
            .build();
        given(categoryRepository.findByCategoryName(anyString()))
            .willReturn(Optional.of(Category.builder().build()));

        // when
        CategoryException exception = assertThrows(CategoryException.class,
            () -> categoryService.register(request));

        // then
        assertEquals(ALREADY_EXIST_CATEGORY_NAME, exception.getErrorCode());
    }

    @Test
    void 카테고리_등록_성공() {
        // given
        String categoryName = "스트릿";
        CategoryDto.Request request = CategoryDto.Request.builder()
            .categoryName(categoryName)
            .build();
        given(categoryRepository.findByCategoryName(anyString()))
            .willReturn(Optional.empty());

        // when
        categoryService.register(request);

        // then
    }
}