package com.zerobase.fitme.service;

import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.model.RegBrand;
import com.zerobase.fitme.repository.BrandRepository;
import com.zerobase.fitme.type.ErrorCode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    /**
     * 브랜드 등록
     * @param request
     */
    public void register(RegBrand.Request request) {
        if(brandRepository.findByBrandName(request.getBrandName()).isPresent()){
            throw new BrandException(ErrorCode.ALREADY_EXIST_BRAND_NAME);
        }

        brandRepository.save(Brand.builder()
                .brandName(request.getBrandName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .regDt(LocalDate.now())
                .build());
        }
}
