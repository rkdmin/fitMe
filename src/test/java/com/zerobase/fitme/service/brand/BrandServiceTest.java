package com.zerobase.fitme.service.brand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

import com.zerobase.fitme.dto.BrandDto;
import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.type.BrandErrorCode;
import com.zerobase.fitme.repository.BrandRepository;
import com.zerobase.fitme.service.BrandService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

class BrandServiceTest {
    @ExtendWith(MockitoExtension.class)

    @Mock
    private BrandRepository brandRepository;
    @InjectMocks
    private BrandService brandService;

    @Test
    void 브랜드_등록_실패_해당브랜드가없음() {
        // given
        String brandName = "몽블랑";

        given(brandRepository.findByBrandName(anyString()))
            .willReturn(Optional.of(createBrand(brandName)));

        // when
        BrandException exception = assertThrows(BrandException.class,
            () -> brandService.register(BrandDto.Request.builder().brandName(brandName).build()));

        // then
        assertEquals(BrandErrorCode.ALREADY_EXIST_BRAND_NAME, exception.getErrorCode());
    }

    @Test
    void 브랜드_등록_성공() {
        // given

        given(brandRepository.findByBrandName(anyString()))
            .willReturn(Optional.empty());

        // when
        brandService.register(BrandDto.Request.builder().brandName("몽블랑").build());

        // then
    }

    

    private Brand createBrand(String name){
        return Brand.builder().brandName(name).build();
    }
//        @Test
//        @DisplayName("모임 신청 성공")
//        void partyApplyTestSuccess() {
//            // given
//            Long memberId = 1L;
//            Long partyId = 1L;
//            given(partyRepository.findById(anyLong()))
//                .willReturn(Optional.of(Party.builder()
//                    .id(partyId)
//                    .build()));
//            given(partyApplyRepository.existsByTargetMemberIdAndParty_Id(anyLong(), anyLong()))
//                .willReturn(false);
//            given(partyApplyRepository.save(any()))
//                .willReturn(PartyApply.builder()
//                    .id(1L)
//                    .targetMemberId(1L)
//                    .isAccept(false)
//                    .party(Party.builder().id(partyId).build())
//                    .build());
//            ArgumentCaptor<PartyApply> captor = ArgumentCaptor.forClass(PartyApply.class);
//            // when
//            PartyApplyDto.Response response = partyApplyService.applyParty(memberId, partyId);
//
//            // then
//            verify(partyApplyRepository, times(1)).save(captor.capture());
//            assertEquals(partyId, captor.getValue().getParty().getId());
//            assertEquals(false, response.isAccept());
//            assertEquals(1L, response.getPartyId());
//        }
//
//        @Test
//        @DisplayName("모임 신청 실패 - 이미 신청한 모임")
//        void partyApplyTestFailedPartyNotFound() {
//            // given
//            given(partyRepository.findById(anyLong()))
//                .willReturn(Optional.of(Party.builder().build()));
//            given(partyApplyRepository.existsByTargetMemberIdAndParty_Id(anyLong(), any()))
//                .willReturn(true);
//
//            // when
//            PartyApplyException exception = assertThrows(PartyApplyException.class,
//                () -> partyApplyService.applyParty(1L, 1L));
//
//            // then
//            assertEquals(PartyApplyErrorCode.ALREADY_BEAN_APPLIED.getErrorCode(), exception.getErrorCode());
//        }
}