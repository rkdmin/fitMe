package com.zerobase.fitme.iteminfo;

import static com.zerobase.fitme.exception.type.ItemInfoErrorCode.INVALID_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zerobase.fitme.dto.ItemInfoDto;
import com.zerobase.fitme.exception.ItemInfoException;
import com.zerobase.fitme.repository.ItemInfoRepository;
import com.zerobase.fitme.service.ItemInfoService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemInfoServiceTest {
    @Mock
    private ItemInfoRepository itemInfoRepository;

    @InjectMocks
    private ItemInfoService itemInfoService;

    @Test
    void 상품_상세정보_등록_실패_잘못된색상() {
        // given
        ItemInfoDto request = ItemInfoDto.builder()
            .material("솜")
            .colorList(List.of("red", "white", "lemon"))// 레몬이란 색상은 없음
            .sizeList(List.of("l", "free"))
            .build();

        // when
        ItemInfoException exception = assertThrows(ItemInfoException.class,
            () -> itemInfoService.register(request));

        // then
        assertEquals(INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 상품_상세정보_등록_실패_잘못된사이즈() {
        // given
        ItemInfoDto request = ItemInfoDto.builder()
            .material("솜")
            .colorList(List.of("red", "white"))
            .sizeList(List.of("l", "free", "xxxl"))// xxxl란 사이즈는 없음
            .build();

        // when
        ItemInfoException exception = assertThrows(ItemInfoException.class,
            () -> itemInfoService.register(request));

        // then
        assertEquals(INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 상품_상세정보_등록_성공() {
        // given
        ItemInfoDto request = ItemInfoDto.builder()
            .material("솜")
            .colorList(List.of("red", "white"))
            .sizeList(List.of("l", "free"))// xxxl란 사이즈는 없음
            .build();

        // when
        itemInfoService.register(request);

        // then
    }
}