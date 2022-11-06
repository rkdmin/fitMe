package com.zerobase.fitme.service;

import com.zerobase.fitme.entity.Seller;
import com.zerobase.fitme.model.RegSeller;
import com.zerobase.fitme.repository.SellerRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    /**
     * 샐러 등록
     * @param request
     */
    public void register(RegSeller.Request request) {
        sellerRepository.save(Seller.builder()
                .companyName(request.getCompanyName())
                .sellerName(request.getSellerName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .businessNumber(request.getBusinessNumber())
                .email(request.getEmail())
                .regDt(LocalDateTime.now())
                .build());
    }

    /**
     * 판매자 조회
     * @return
     */
    public List<Seller> read() {
        return sellerRepository.findAll();
    }
//
//    /**
//     * 브랜드 수정
//     * @param request
//     * @return Brand
//     */
//    public Brand patch(UdtBrand.Request request) {
//        validationPatch(request);
//
//        Brand brand = brandRepository.findById(request.getId()).orElseThrow(() ->
//            new BrandException(BRAND_NOT_FOUND)
//        );
//
//        brand.patch(request);
//
//        return brandRepository.save(brand);
//    }
//
//    /**
//     * 브렌드 삭제
//     * @param id
//     */
//    public void delete(Long id) {
//        brandRepository.delete(
//            brandRepository.findById(id).orElseThrow(() -> new BrandException(BRAND_NOT_FOUND))
//        );
//    }
//
//    private void validationPatch(UdtBrand.Request request) {
//        if(!StringUtils.hasText(request.getBrandName()) && !StringUtils.hasText(request.getPhone())
//            && !StringUtils.hasText(request.getUrl())){
//            throw new BrandException(INVALID_REQUEST);
//        }
//        if(brandRepository.findByBrandName(request.getBrandName()).isPresent()){
//            throw new BrandException(ALREADY_EXIST_BRAND_NAME);
//        }
//    }


}
