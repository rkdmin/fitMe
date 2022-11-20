package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.CartErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.dto.CartDto;
import com.zerobase.fitme.dto.OrderDto;
import com.zerobase.fitme.dto.OrderDto.Response;
import com.zerobase.fitme.entity.Cart;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.entity.Order;
import com.zerobase.fitme.exception.CartException;
import com.zerobase.fitme.repository.OrderRepository;
import com.zerobase.fitme.type.ColorType;
import com.zerobase.fitme.type.OrderStatus;
import com.zerobase.fitme.type.SizeType;
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

    /**
     * 주문 등록
     * @param username
     * @param itemId
     */
    public void register(String username, Long itemId) {
        // 멤버와 아이템 불러오기
        Member member = memberService.findByUserName(username);
        Item item = itemService.findByItemId(itemId);

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
}
