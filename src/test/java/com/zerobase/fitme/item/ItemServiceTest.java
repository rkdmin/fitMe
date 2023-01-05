package com.zerobase.fitme.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.dto.BrandDto;
import com.zerobase.fitme.dto.ItemDto;
import com.zerobase.fitme.dto.ItemInfoDto;
import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.ItemCategory;
import com.zerobase.fitme.entity.ItemInfo;
import com.zerobase.fitme.entity.Model;
import com.zerobase.fitme.entity.Seller;
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
import com.zerobase.fitme.type.ColorType;
import com.zerobase.fitme.type.SizeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    @Test
    void 상품_등록_성공() {
        // given
        String itemName = "거위털패딩";
        ItemDto.Request request = ItemDto.Request.builder()
            .itemName(itemName)
            .price(1000L)
            .saleRate(10L)
            .cnt(10L)
            .brandId(1L)
            .sellerId(1L)
            .modelId(1L)
            .regItemInfo(ItemInfoDto.builder()
                .colorList(List.of("red", "white"))
                .sizeList(List.of("l", "xl"))
                .build())
            .categoryNameList(List.of("신발"))
            .build();

        given(itemRepository.existsByItemName(anyString()))
            .willReturn(false);
        given(brandService.readById(anyLong()))
            .willReturn(Optional.of(Brand.builder().build()));
        given(sellerService.readById(anyLong()))
            .willReturn(Optional.of(Seller.builder().build()));
        given(modelService.readById(anyLong()))
            .willReturn(Optional.of(Model.builder().build()));

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);

        // when
        itemService.register(request);

        // then
        verify(itemRepository, times(1)).save(captor.capture());
        assertEquals(0, captor.getValue().getView());// 조회수 0인지 체크
        assertEquals(900, captor.getValue().getSalePrice());// 할인율 계산 확인
        assertEquals(LocalDateTime.now().getMinute(), captor.getValue().getRegDt().getMinute());// 시간 확인
        assertEquals(itemName, captor.getValue().getItemName());// 상품명 확인
    }

    @Test
    void 상품_상세정보_조회_실패_상품이없음() {
        // given
        Long itemId = 1L;

        given(itemRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        // when
        ItemException exception = assertThrows(ItemException.class,
            () -> itemService.readDetail(itemId));

        // then
        assertEquals(ItemErrorCode.ITEM_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 상품_상세정보_조회_성공() {
        // given
        Long itemId = 1L;
        long view = 100L;// 조회수 100(결과는 1높아야함)
        given(itemRepository.findById(anyLong()))
            .willReturn(Optional.of(Item.builder().view(view).build()));
        given(itemRepository.save(any()))
            .willReturn(Item.builder()
                .itemInfo(ItemInfo.builder().material("123").colorList(List.of(ColorType.RED)).sizeList(List.of(
                    SizeType.L)).build())
                .brand(Brand.builder().build())
                .seller(Seller.builder().build())
                .model(Model.builder().build())
                .itemCategoryList(List.of(ItemCategory.builder().category(Category.builder().build()).build()))
                .build());

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);

        // when
        itemService.readDetail(itemId);

        // then
        verify(itemRepository, times(1)).save(captor.capture());
        assertEquals(view + 1, captor.getValue().getView());
    }
}