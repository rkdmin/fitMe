package com.zerobase.fitme.service.category;

import static com.zerobase.fitme.exception.type.CategoryErrorCode.ALREADY_EXIST_CATEGORY_NAME;
import static com.zerobase.fitme.exception.type.CategoryErrorCode.CATEGORY_NOT_FOUND;
import static com.zerobase.fitme.exception.type.CategoryErrorCode.INVALID_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.dto.CategoryDto;
import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.exception.CategoryException;
import com.zerobase.fitme.model.UdtCategory;
import com.zerobase.fitme.repository.CategoryRepository;
import com.zerobase.fitme.service.CategoryService;
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

    @Test
    void 카테고리_수정_실패_수정값이없음() {
        // given
        UdtCategory.Request request = UdtCategory.Request.builder()
            .categoryName(null)// 수정값이없음
            .build();

        // when
        CategoryException exception = assertThrows(CategoryException.class,
            () -> categoryService.patch(request));

        // then
        assertEquals(INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 카테고리_수정_실패_존재하는카테고리명() {
        // given
        String newCategoryName = "신발";
        String categoryName = "신발";
        UdtCategory.Request request = UdtCategory.Request.builder()
            .categoryName(newCategoryName)
            .build();

        given(categoryRepository.findByCategoryName(anyString()))
            .willReturn(Optional.of(Category.builder()
                    .categoryName(categoryName)// 이미 존재하는 카테고리명
                    .build()));

        // when
        CategoryException exception = assertThrows(CategoryException.class,
            () -> categoryService.patch(request));

        // then
        assertEquals(ALREADY_EXIST_CATEGORY_NAME, exception.getErrorCode());
    }

    @Test
    void 카테고리_수정_실패_해당카테고리가없음() {
        // given
        String newCategoryName = "신발";
        UdtCategory.Request request = UdtCategory.Request.builder()
            .id(1L)
            .categoryName(newCategoryName)
            .build();

        given(categoryRepository.findByCategoryName(anyString()))
            .willReturn(Optional.empty());
        given(categoryRepository.findById(anyLong()))
            .willReturn(Optional.empty());// 카테고리가 없음

        // when
        CategoryException exception = assertThrows(CategoryException.class,
            () -> categoryService.patch(request));

        // then
        assertEquals(CATEGORY_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 카테고리_수정_성공() {
        // given
        String newCategoryName = "신발";
        String categoryName = "상의";
        UdtCategory.Request request = UdtCategory.Request.builder()
            .id(1L)
            .categoryName(newCategoryName)
            .build();

        given(categoryRepository.findByCategoryName(anyString()))
            .willReturn(Optional.empty());
        given(categoryRepository.findById(anyLong()))
            .willReturn(Optional.of(Category.builder()
                    .categoryName(categoryName)// 기존 카테고리명
                    .build()));// 카테고리가 없음

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);

        // when
        categoryService.patch(request);

        // then
        verify(categoryRepository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getCategoryName(), newCategoryName);
    }
}