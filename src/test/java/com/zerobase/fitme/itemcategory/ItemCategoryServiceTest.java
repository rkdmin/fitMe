package com.zerobase.fitme.itemcategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.ItemCategory;
import com.zerobase.fitme.repository.ItemCategoryRepository;
import com.zerobase.fitme.service.ItemCategoryService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemCategoryServiceTest {
    @Mock
    private ItemCategoryRepository itemCategoryRepository;

    @InjectMocks
    private ItemCategoryService itemCategoryService;

    @Test
    void 상품_카테고리_등록_성공() {
        // given
        Item item = Item.builder().id(1L).build();
        List<Category> categoryList = new ArrayList<>();
        int categoryCnt = 5;// 카테고리개수
        for(long i = 1; i <= categoryCnt; i++){
            categoryList.add(Category.builder().id(i).build());
        }

        ArgumentCaptor<ItemCategory> captor = ArgumentCaptor.forClass(ItemCategory.class);

        // when
        itemCategoryService.register(item, categoryList);

        // then
        verify(itemCategoryRepository, times(categoryCnt)).save(captor.capture());
        assertEquals(captor.getValue().getCategory().getId(), categoryCnt);
    }
}