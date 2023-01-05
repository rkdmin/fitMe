package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.OrderErrorCode.ALREADY_START_ITEM;
import static com.zerobase.fitme.exception.type.OrderErrorCode.INVALID_REQUEST;
import static com.zerobase.fitme.exception.type.OrderErrorCode.ORDER_NOT_FOUND;
import static com.zerobase.fitme.exception.type.OrderErrorCode.OUT_OF_STOCK;

import com.zerobase.fitme.dto.OrderDto;
import com.zerobase.fitme.dto.OrderDto.RequestPatch;
import com.zerobase.fitme.dto.OrderDto.Response;
import com.zerobase.fitme.dto.OrderDto.ResponseManager;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.entity.Order;
import com.zerobase.fitme.exception.OrderException;
import com.zerobase.fitme.repository.OrderRepository;
import com.zerobase.fitme.type.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 주문리스트 조회(회원)
     */
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
        Item item = order.getItem();
        item.setCnt(item.getCnt() + 1);
        itemService.update(item);

        orderRepository.delete(order);
        return OrderDto.Response.toDto(order);
    }

    /**
     * 준비중인 주문리스트 조회(매니저, 관리자)
     * @param pageable
     * @return
     */
    public Page<ResponseManager> readStatusReady(Pageable pageable) {
        return orderRepository.findByOrderStatusOrderByIdDesc(OrderStatus.준비중, pageable)
            .map(ResponseManager::toDto);
    }

    /**
     * 주문 상태 변경
     * @param request
     * @param orderId
     * @return
     */
    public ResponseManager patchOrderStatus(RequestPatch request, Long orderId) {
        OrderStatus orderStatus = OrderStatus.getType(request.getOrderStatus());
        if(orderStatus == null){
            throw new OrderException(INVALID_REQUEST);
        }
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new OrderException(ORDER_NOT_FOUND)
        );

        // 주문 상태 갱신
        order.setOrderStatus(orderStatus);
        return ResponseManager.toDto(orderRepository.save(order));
    }
}
