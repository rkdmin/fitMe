package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.CartErrorCode.CART_NOT_FOUND;
import static com.zerobase.fitme.exception.type.OrderErrorCode.ALREADY_START_ITEM;
import static com.zerobase.fitme.exception.type.OrderErrorCode.ORDER_NOT_FOUND;
import static com.zerobase.fitme.exception.type.OrderErrorCode.OUT_OF_STOCK;

import com.zerobase.fitme.dto.CartDto;
import com.zerobase.fitme.dto.OrderDto;
import com.zerobase.fitme.dto.OrderDto.Response;
import com.zerobase.fitme.entity.Cart;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.entity.Order;
import com.zerobase.fitme.exception.CartException;
import com.zerobase.fitme.exception.OrderException;
import com.zerobase.fitme.exception.type.OrderErrorCode;
import com.zerobase.fitme.repository.OrderRepository;
import com.zerobase.fitme.type.OrderStatus;
import java.time.LocalDateTime;
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
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final ItemService itemService;
    private final CartService cartService;

    /**
     * 주문 등록
     * @param username
     * @param itemId
     */
    public void register(String username, Long itemId) {
        // 멤버와 아이템 불러오기
        Member member = memberService.findByUserName(username);
        Item item = itemService.findByItemId(itemId);

        // item 재고 업데이트
        long curCnt = item.getCnt() - 1;
        if(curCnt < 0){
            throw new OrderException(OUT_OF_STOCK);
        }
        item.setCnt(curCnt);

        // 장바구니에 해당 상품이 있을 경우 삭제
        List<Long> cartIdList = cartService.findByMemberAndItem(member.getId(), item.getId());
        for(Long cartId: cartIdList){
            if(cartId != null){
                cartService.delete(cartId);
            }
        }

        orderRepository.save(Order.builder()
                .member(member)
                .item(item)
                .orderStatus(OrderStatus.준비중)
                .regDt(LocalDateTime.now())
                .build());
    }

    public List<Response> readOrderList(String username) {
        // Order -> OrderDto.Response
        return orderRepository.findByMember_Username(username).stream()
            .map(x -> OrderDto.Response.toDto(x))
            .collect(Collectors.toList());
    }

    /**
     * 주문 취소
     */
    public OrderDto.Response delete(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new OrderException(ORDER_NOT_FOUND)
        );

        // 배송을 이미 시작한 경우
        if(order.getOrderStatus() != OrderStatus.준비중){
            throw new OrderException(ALREADY_START_ITEM);
        }

        // 상품재고+1
        Item item = itemService.findByItemId(order.getId());
        item.setCnt(item.getCnt() + 1);
        itemService.update(item);

        orderRepository.delete(order);
        return OrderDto.Response.toDto(order);
    }
}
