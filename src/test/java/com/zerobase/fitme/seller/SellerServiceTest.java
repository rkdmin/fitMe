package com.zerobase.fitme.seller;

import static com.zerobase.fitme.exception.type.SellerErrorCode.INVALID_REQUEST;
import static com.zerobase.fitme.exception.type.SellerErrorCode.SELLER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.entity.Seller;
import com.zerobase.fitme.exception.SellerException;
import com.zerobase.fitme.model.UdtSeller;
import com.zerobase.fitme.repository.SellerRepository;
import com.zerobase.fitme.service.SellerService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SellerServiceTest {

    @Mock
    private SellerRepository sellerRepository;
    @InjectMocks
    private SellerService sellerService;

    @Test// 로직이 없음
    void 판매자_등록_성공() {
        // given

        // when

        // then
    }

    @Test// 로직이 없음
    void 모델_조회_성공() {
        // given

        // when

        // then
    }

    @Test
    void 판매자_수정_실패_요청값이없음() {
        // given
        UdtSeller.Request request = UdtSeller.Request.builder().build();// 값이 하나도 없음
        Long sellerId = 1L;

        // when
        SellerException exception = assertThrows(SellerException.class,
            () -> sellerService.patch(request, sellerId));

        // then
        assertEquals(INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 판매자_수정_실패_판매자가없음() {
        // given
        UdtSeller.Request request = UdtSeller.Request.builder()
            .phone("010-1234-5678")
            .build();
        Long sellerId = 1L;

        given(sellerRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        // when
        SellerException exception = assertThrows(SellerException.class,
            () -> sellerService.patch(request, sellerId));

        // then
        assertEquals(SELLER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 판매자_수정_성공() {
        // given
        String requestPhone = "010-1234-5678";// 변경할 전화번호
        String phone = "010-9876-5432";// 기존 전화번호
        UdtSeller.Request request = UdtSeller.Request.builder()
            .phone(requestPhone)
            .build();
        Long sellerId = 1L;

        given(sellerRepository.findById(anyLong()))
            .willReturn(Optional.of(Seller.builder().phone(phone).build()));

        ArgumentCaptor<Seller> captor = ArgumentCaptor.forClass(Seller.class);

        // when
        sellerService.patch(request, sellerId);

        // then
        verify(sellerRepository, times(1)).save(captor.capture());
        assertEquals(requestPhone, captor.getValue().getPhone());
    }

    @Test
    void 판매자_삭제_실패_판매자가없음() {
        // given
        Long sellerId = 1L;

        given(sellerRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        // when
        SellerException exception = assertThrows(SellerException.class,
            () -> sellerService.delete(sellerId));

        // then
        assertEquals(SELLER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 판매자_삭제_성공() {
        // given
        Long sellerId = 1L;

        given(sellerRepository.findById(anyLong()))
            .willReturn(Optional.of(Seller.builder().build()));

        // when
        sellerService.delete(sellerId);

        // then
    }
}