package com.zerobase.fitme.order;

import static com.zerobase.fitme.exception.type.OrderErrorCode.ALREADY_START_ITEM;
import static com.zerobase.fitme.exception.type.OrderErrorCode.INVALID_REQUEST;
import static com.zerobase.fitme.exception.type.OrderErrorCode.ORDER_NOT_FOUND;
import static com.zerobase.fitme.exception.type.OrderErrorCode.OUT_OF_STOCK;
import static com.zerobase.fitme.type.OrderStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.dto.OrderDto.RequestPatch;
import com.zerobase.fitme.entity.Cart;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.entity.Order;
import com.zerobase.fitme.exception.OrderException;
import com.zerobase.fitme.repository.OrderRepository;
import com.zerobase.fitme.service.CartService;
import com.zerobase.fitme.service.ItemService;
import com.zerobase.fitme.service.MemberService;
import com.zerobase.fitme.service.OrderService;
import com.zerobase.fitme.type.OrderStatus;
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
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private ItemService itemService;
    @Mock
    private CartService cartService;
    @InjectMocks
    private OrderService orderService;

    @Test
    void 주문_등록_실패_재고가없음() {
        // given
        String username = "user123";
        Long itemId = 1L;
        long cnt = 0;// 재고가 떨어짐
        Member member = Member.builder().build();
        Item item = Item.builder().cnt(cnt).build();
        given(memberService.findByUserName(anyString()))
            .willReturn(member);
        given(itemService.findByItemId(anyLong()))
            .willReturn(item);

        // when
        OrderException exception = assertThrows(OrderException.class,
            () -> orderService.register(username, itemId));

        // then
        assertEquals(OUT_OF_STOCK, exception.getErrorCode());
    }

    @Test
    void 주문_등록_성공() {
        // given
        String username = "user123";
        Long itemId = 1L;
        long cnt = 10;// 재고 10개
        Member member = Member.builder().id(1L).build();
        Item item = Item.builder().id(1L).cnt(cnt).build();
        List<Long> cartIdList = List.of(1L, 2L, 3L);// 장바구니 리스트

        given(memberService.findByUserName(anyString()))
            .willReturn(member);
        given(itemService.findByItemId(anyLong()))
            .willReturn(item);
        given(cartService.findByMemberAndItem(anyLong(), anyLong()))
            .willReturn(cartIdList);// 3개의 장바구니

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<Long> captor2 = ArgumentCaptor.forClass(Long.class);

        // when
        orderService.register(username, itemId);

        // then
        verify(orderRepository, times(1)).save(captor.capture());
        verify(cartService, times(cartIdList.size())).delete(captor2.capture());
        assertEquals(cnt - 1, captor.getValue().getItem().getCnt());// 재고확인
        assertEquals(LocalDateTime.now().getMinute(), captor.getValue().getRegDt().getMinute());// 날짜확인
        assertEquals(준비중, captor.getValue().getOrderStatus());// 준비중인지확인
        assertEquals(cartIdList.get(cartIdList.size() - 1), captor2.getValue());// 장바구니가 잘 삭제됐는지 확인
    }

    @Test
    void 주문_취소_실패_주문이없음() {
        // given
        Long orderId = 1L;

        given(orderRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        // when
        OrderException exception = assertThrows(OrderException.class,
            () -> orderService.delete(orderId));

        // then
        assertEquals(ORDER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 주문_취소_실패_배송이시작됨() {
        // given
        Long orderId = 1L;

        given(orderRepository.findById(anyLong()))
            .willReturn(Optional.of(Order.builder()
                    .orderStatus(배송완료)// 이미 배송이 완료
                    .build()));

        // when
        OrderException exception = assertThrows(OrderException.class,
            () -> orderService.delete(orderId));

        // then
        assertEquals(ALREADY_START_ITEM, exception.getErrorCode());
    }

    @Test
    void 주문_취소_성공() {
        // given
        Long orderId = 1L;
        int cnt = 10;// 재고 10개(1개 늘어나야됨)
        Item item = Item.builder()
            .id(1L)
            .cnt(cnt)
            .build();
        given(orderRepository.findById(anyLong()))
            .willReturn(Optional.of(Order.builder()
                    .id(1L)
                    .item(item)
                    .orderStatus(준비중)
                    .build()));

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);

        // when
        orderService.delete(orderId);

        // then
        verify(itemService, times(1)).update(captor.capture());
        assertEquals(cnt + 1, captor.getValue().getCnt());// 재고 늘어남
    }

    @Test
    void 주문_상태_변경_실패_잘못된주문상태() {
        // given
        RequestPatch request = RequestPatch.builder()
            .orderStatus("환불중")// 잘못된 주문상태
            .build();
        Long orderId = 1L;

        // when
        OrderException exception = assertThrows(OrderException.class,
            () -> orderService.patchOrderStatus(request, orderId));

        // then
        assertEquals(INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 주문_상태_변경_실패_주문이없음() {
        // given
        RequestPatch request = RequestPatch.builder()
            .orderStatus("준비중")
            .build();
        Long orderId = 1L;

        given(orderRepository.findById(anyLong()))
            .willReturn(Optional.empty());// 주문이 없음

        // when
        OrderException exception = assertThrows(OrderException.class,
            () -> orderService.patchOrderStatus(request, orderId));

        // then
        assertEquals(ORDER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 주문_상태_변경_성공() {
        // given
        String requestStatus = "배송완료";// 바꿀 주문상태
        OrderStatus status = 준비중;// 원래 주문상태
        RequestPatch request = RequestPatch.builder()
            .orderStatus(requestStatus)
            .build();
        Long orderId = 1L;
        Order order = Order.builder()
            .orderStatus(status)// 원래 준비중
            .member(Member.builder().build())
            .item(Item.builder().build())
            .build();
        given(orderRepository.findById(anyLong()))
            .willReturn(Optional.of(order));
        given(orderRepository.save(any()))
            .willReturn(order);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        // when
        orderService.patchOrderStatus(request, orderId);

        // then
        verify(orderRepository, times(1)).save(captor.capture());
        // 배송상태 변경됐는지 확인
        assertEquals(OrderStatus.getType(requestStatus), captor.getValue().getOrderStatus());
    }
}