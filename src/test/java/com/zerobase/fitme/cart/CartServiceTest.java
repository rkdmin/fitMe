package com.zerobase.fitme.cart;

import static com.zerobase.fitme.type.ColorType.*;
import static com.zerobase.fitme.type.SizeType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.dto.BrandDto;
import com.zerobase.fitme.dto.CartDto;
import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.entity.Cart;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.ItemInfo;
import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.CartException;
import com.zerobase.fitme.exception.type.BrandErrorCode;
import com.zerobase.fitme.exception.type.CartErrorCode;
import com.zerobase.fitme.model.UdtBrand;
import com.zerobase.fitme.repository.BrandRepository;
import com.zerobase.fitme.repository.CartRepository;
import com.zerobase.fitme.service.BrandService;
import com.zerobase.fitme.service.CartService;
import com.zerobase.fitme.service.ItemService;
import com.zerobase.fitme.service.MemberService;
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
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private ItemService itemService;

    @InjectMocks
    private CartService cartService;

    @Test
    void 장바구니_등록_실패_존재하지않은색상() {
        // given
        CartDto.Request request = CartDto.Request.builder()
            .color("sky")// 존재하지 않음
            .size("s")
            .build();

        // when
        CartException exception = assertThrows(CartException.class,
            () -> cartService.register(null, null, request));

        // then
        assertEquals(CartErrorCode.INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 장바구니_등록_실패_존재하지않은사이즈() {
        // given
        CartDto.Request request = CartDto.Request.builder()
            .color("white")
            .size("xxxl")// 존재하지 않음
            .build();

        // when
        CartException exception = assertThrows(CartException.class,
            () -> cartService.register(null, null, request));

        // then
        assertEquals(CartErrorCode.INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 장바구니_등록_실패_등록된상품에색상이없음() {
        // given
        // 요청 값
        CartDto.Request request = CartDto.Request.builder()
            .color("white")// white 요청
            .size("xl")
            .build();
        // 색상 리스트(해당 아이템엔 red와 gray만 존재)
        List<ColorType> colorList = List.of(RED, GRAY);
        List<SizeType> sizeList = List.of(L, FREE);
        Item item = Item.builder()
            .itemInfo(ItemInfo.builder().colorList(colorList).sizeList(sizeList).build())
            .build();

        given(memberService.findByUserName(anyString()))
            .willReturn(Member.builder().build());
        given(itemService.findByItemId(anyLong()))
            .willReturn(item);

        // when
        CartException exception = assertThrows(CartException.class,
            () -> cartService.register("유저명", 1L, request));

        // then
        assertEquals(CartErrorCode.EMPTY_COLOR_OR_SIZE, exception.getErrorCode());
    }

    @Test
    void 장바구니_등록_실패_등록된상품에사이즈가없음() {
        // given
        // 요청 값
        CartDto.Request request = CartDto.Request.builder()
            .color("white")
            .size("xl")// xl 요청
            .build();
        // 사이즈 리스트(해당 아이템엔 l와 free만 존재)
        List<SizeType> sizeList = List.of(L, FREE);
        List<ColorType> colorList = List.of(RED, GRAY);
        Item item = Item.builder()
            .itemInfo(ItemInfo.builder().sizeList(sizeList).colorList(colorList).build())
            .build();

        given(memberService.findByUserName(anyString()))
            .willReturn(Member.builder().build());
        given(itemService.findByItemId(anyLong()))
            .willReturn(item);

        // when
        CartException exception = assertThrows(CartException.class,
            () -> cartService.register("유저명", 1L, request));

        // then
        assertEquals(CartErrorCode.EMPTY_COLOR_OR_SIZE, exception.getErrorCode());
    }

    @Test
    void 장바구니_등록_성공() {
        // given
        // 요청 값
        CartDto.Request request = CartDto.Request.builder()
            .color("red")
            .size("free")
            .build();
        // 사이즈 리스트(해당 아이템엔 l와 free만 존재)
        List<SizeType> sizeList = List.of(L, FREE);
        List<ColorType> colorList = List.of(RED, GRAY);
        Item item = Item.builder()
            .itemInfo(ItemInfo.builder().sizeList(sizeList).colorList(colorList).build())
            .build();

        given(memberService.findByUserName(anyString()))
            .willReturn(Member.builder().build());
        given(itemService.findByItemId(anyLong()))
            .willReturn(item);

        // when
        cartService.register("유저명", 1L, request);

        // then
    }

    @Test// 로직이 없음
    void 장바구니_조회_성공() {
    }

    @Test
    void 장바구니_수정_실패_장바구니가없음() {
        // given
        given(cartRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        // when
        CartException exception = assertThrows(CartException.class,
            () -> cartService.patch(1L, null));

        // then
        assertEquals(CartErrorCode.CART_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 장바구니_수정_실패_수정값이없음() {
        // given
        // 요청 값
        CartDto.RequestPatch request = CartDto.RequestPatch.builder()
            .color(null)
            .size(null)// 수정값없음
            .build();

        given(cartRepository.findById(anyLong()))
            .willReturn(Optional.of(Cart.builder().build()));

        // when
        CartException exception = assertThrows(CartException.class,
            () -> cartService.patch(1L, request));

        // then
        assertEquals(CartErrorCode.INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 장바구니_수정_실패_상품에색상이없음() {
        // given
        // 요청 값
        CartDto.RequestPatch request = CartDto.RequestPatch.builder()
            .color("red")// red로 변경
            .size(null)
            .build();
        // 사이즈 리스트(해당 아이템엔 l와 free만 존재)
        List<SizeType> sizeList = List.of(L, FREE);
        List<ColorType> colorList = List.of(YELLOW, GRAY);// red가 없음
        Cart cart = Cart.builder()
            .item(Item.builder()
                .itemInfo(ItemInfo.builder().sizeList(sizeList).colorList(colorList).build())
                .build())
            .build();

        given(cartRepository.findById(anyLong()))
            .willReturn(Optional.of(cart));

        // when
        CartException exception = assertThrows(CartException.class,
            () -> cartService.patch(1L, request));

        // then
        assertEquals(CartErrorCode.EMPTY_COLOR_OR_SIZE, exception.getErrorCode());
    }

    @Test
    void 장바구니_수정_실패_상품에사이즈가없음() {
        // given
        // 요청 값
        CartDto.RequestPatch request = CartDto.RequestPatch.builder()
            .color(null)
            .size("free")// free로 변경
            .build();
        // 사이즈 리스트(해당 아이템엔 l와 free만 존재)
        List<SizeType> sizeList = List.of(L, XL);// free가 없음
        List<ColorType> colorList = List.of(YELLOW, GRAY);
        Cart cart = Cart.builder()
            .item(Item.builder()
                    .itemInfo(ItemInfo.builder().sizeList(sizeList).colorList(colorList).build())
                    .build())
            .build();

        given(cartRepository.findById(anyLong()))
            .willReturn(Optional.of(cart));

        // when
        CartException exception = assertThrows(CartException.class,
            () -> cartService.patch(1L, request));

        // then
        assertEquals(CartErrorCode.EMPTY_COLOR_OR_SIZE, exception.getErrorCode());
    }

    @Test
    void 장바구니_수정_성공() {
        // given
        String requestColor = "red";// red로 수정
        String requestSize = "free";// free로 수정
        CartDto.RequestPatch request = CartDto.RequestPatch.builder()
            .color(requestColor)
            .size(requestSize)
            .build();
        List<SizeType> sizeList = List.of(L, XL, FREE);
        List<ColorType> colorList = List.of(YELLOW, GRAY, RED);
        Cart cart = Cart.builder()
            .item(Item.builder()
                .itemInfo(ItemInfo.builder().sizeList(sizeList).colorList(colorList).build())
                .build())
            .color(YELLOW)// 기존 색
            .size(L)// 기존 사이즈
            .build();

        given(cartRepository.findById(anyLong()))
            .willReturn(Optional.of(cart));
        given(cartRepository.save(any()))
            .willReturn(cart);

        ArgumentCaptor<Cart> captor = ArgumentCaptor.forClass(Cart.class);

        // when
        cartService.patch(1L, request);

        // then
        verify(cartRepository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getColor(), ColorType.getType(requestColor));
        assertEquals(captor.getValue().getSize(), SizeType.getType(requestSize));
    }

    @Test
    void 장바구니_삭제_실패_장바구니가없음() {
        // given
        given(cartRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        // when
        CartException exception = assertThrows(CartException.class,
            () -> cartService.delete(1L));

        // then
        assertEquals(CartErrorCode.CART_NOT_FOUND, exception.getErrorCode());
    }
}