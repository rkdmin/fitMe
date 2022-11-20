package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.CartErrorCode.CART_NOT_FOUND;
import static com.zerobase.fitme.exception.type.CartErrorCode.EMPTY_COLOR_OR_SIZE;
import static com.zerobase.fitme.exception.type.CartErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.dto.CartDto;
import com.zerobase.fitme.dto.CartDto.Response;
import com.zerobase.fitme.dto.ItemDto;
import com.zerobase.fitme.entity.Cart;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.exception.CartException;
import com.zerobase.fitme.dto.CartDto.Request;
import com.zerobase.fitme.exception.type.CartErrorCode;
import com.zerobase.fitme.repository.CartRepository;
import com.zerobase.fitme.type.ColorType;
import com.zerobase.fitme.type.SizeType;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final MemberService memberService;
    private final ItemService itemService;

    /**
     * 장바구니 등록
     * @param username
     * @param itemId
     * @param request
     */
    public void register(String username, Long itemId, Request request) {
        // 알맞은 색상 또는 사이즈가 없을 경우
        ColorType colorType = ColorType.getType(request.getColor());
        SizeType sizeType = SizeType.getType(request.getSize());
        if(colorType == null || sizeType == null){
            throw new CartException(INVALID_REQUEST);
        }

        // 멤버와 아이템 불러오기
        Member member = memberService.findByUserName(username);
        Item item = itemService.findByItemId(itemId);

        validationSizeOrColor(colorType, sizeType, item);

        cartRepository.save(Cart.builder()
                .member(member)
                .item(item)
                .color(colorType)
                .size(sizeType)
                .build());
    }

    /**
     * 장바구니 리스트 불러오기
     * @param username
     * @return
     */
    public List<CartDto.Response> readCategoryList(String username) {
        // Cart -> ItemDto.Response
        return cartRepository.findByMember_Username(username).stream()
            .map(x -> CartDto.Response.toDto(x))
            .collect(Collectors.toList());
    }

    /**
     * 장바구니 수정
     * @param cartId
     * @param request
     * @return
     */
    public CartDto.Response patch(Long cartId, CartDto.RequestPatch request) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
            () -> new CartException(CART_NOT_FOUND)
        );

        validationSizeOrColor(ColorType.getType(request.getColor()), SizeType.getType(request.getSize()), cart.getItem());

        // 수정
        cart.patch(request);

        // 업데이트
        return Response.toDto(cartRepository.save(cart));
    }

    private static void validationSizeOrColor(ColorType colorType, SizeType sizeType, Item item) {
        // 상품에 색상 또는 사이즈가 있는지 검사
        if(!item.getItemInfo().getColorList().contains(colorType) ||
            !item.getItemInfo().getSizeList().contains(sizeType)){
            throw new CartException(EMPTY_COLOR_OR_SIZE);
        }
    }

}
