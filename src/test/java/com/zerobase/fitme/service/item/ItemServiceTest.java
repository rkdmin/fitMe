package com.zerobase.fitme.service.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.zerobase.fitme.dto.ItemDto;
import com.zerobase.fitme.exception.ItemException;
import com.zerobase.fitme.exception.type.ItemErrorCode;
import com.zerobase.fitme.repository.ItemRepository;
import com.zerobase.fitme.service.BrandService;
import com.zerobase.fitme.service.CategoryService;
import com.zerobase.fitme.service.ItemCategoryService;
import com.zerobase.fitme.service.ItemInfoService;
import com.zerobase.fitme.service.ItemService;
import com.zerobase.fitme.service.ModelService;
import com.zerobase.fitme.service.SellerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private BrandService brandService;
    @Mock
    private ModelService modelService;
    @Mock
    private SellerService sellerService;
    @Mock
    private ItemInfoService itemInfoService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ItemCategoryService itemCategoryService;

    @InjectMocks
    private ItemService itemService;

    @Test
    void 상품_등록_실패_이미존재하는상품명() {
        // given
        String itemName = "거위털패딩";
        ItemDto.Request request = ItemDto.Request.builder().itemName(itemName).build();

        given(itemRepository.existsByItemName(anyString()))
            .willReturn(true);// 상품명이 같음

        // when
        ItemException exception = assertThrows(ItemException.class,
            () -> itemService.register(request));

        // then
        assertEquals(ItemErrorCode.ALREADY_EXIST_ITEM_NAME, exception.getErrorCode());
    }
}