package com.zerobase.fitme.brand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.dto.BrandDto;
import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.type.BrandErrorCode;
import com.zerobase.fitme.model.UdtBrand;
import com.zerobase.fitme.repository.BrandRepository;
import com.zerobase.fitme.service.BrandService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;
    @InjectMocks
    private BrandService brandService;

    @Test
    void 브랜드_등록_실패_해당브랜드가없음() {
        // given
        String brandName = "몽블랑";

        given(brandRepository.findByBrandName(anyString()))
            .willReturn(Optional.of(createBrand(brandName, null, null)));

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

    @Test// 로직이 없음
    void 브랜드_조회_성공() {
        // given

        // when

        // then
    }

    @Test
    void 브랜드_수정_실패_수정값이없음() {
        // given

        // when
        BrandException exception = assertThrows(BrandException.class,
            () -> brandService.patch(UdtBrand.Request.builder()
                .build()));

        // then
        assertEquals(BrandErrorCode.INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 브랜드_수정_실패_이미존재하는브랜드명() {
        // given
        String brandName = "몽블랑";
        Brand brand = createBrand(brandName, null, null);

        given(brandRepository.findByBrandName(anyString()))
            .willReturn(Optional.of(brand));

        // when
        BrandException exception = assertThrows(BrandException.class,
            () -> brandService.patch(UdtBrand.Request.builder()
                .brandName(brandName)// 같은 브랜드명
                .build()));

        // then
        assertEquals(BrandErrorCode.ALREADY_EXIST_BRAND_NAME, exception.getErrorCode());
    }

    @Test
    void 브랜드_수정_실패_해당브랜드가없음() {
        // given
        given(brandRepository.findByBrandName(anyString()))
            .willReturn(Optional.empty());

        // when
        BrandException exception = assertThrows(BrandException.class,
            () -> brandService.patch(UdtBrand.Request.builder()
                    .brandName("몽블랑")
                    .phone("010-1234-5678")
                    .url("http")
                    .build()));

        // then
        assertEquals(BrandErrorCode.BRAND_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 브랜드_수정_성공() {
        // given
        String brandName = "몽블랑";
        String newBrandName = "루이비통";
        Brand brand = createBrand(brandName, null, null);

        given(brandRepository.findById(anyLong()))
            .willReturn(Optional.of(brand));

        // when
        brandService.patch(UdtBrand.Request.builder()
                .id(1L)
                .brandName(newBrandName)
                .build());

        ArgumentCaptor<Brand> captor = ArgumentCaptor.forClass(Brand.class);

        // then
        verify(brandRepository, times(1)).save(captor.capture());
        // 브랜드 이름이 변경됐는지 확인
        assertEquals(captor.getValue().getBrandName(), newBrandName);
    }

    @Test// 로직이 없음
    void 브랜드_삭제_성공() {
        // given

        // when

        // then
    }

    private Brand createBrand(String name, String phone, String url){
        return Brand.builder().brandName(name).phone(phone).url(url).build();
    }
}