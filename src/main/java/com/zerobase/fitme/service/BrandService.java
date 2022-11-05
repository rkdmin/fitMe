package com.zerobase.fitme.service;

import static com.zerobase.fitme.type.ErrorCode.*;
import static com.zerobase.fitme.type.ErrorCode.ALREADY_EXIST_CATEGORY_NAME;
import static com.zerobase.fitme.type.ErrorCode.CATEGORY_NOT_FOUND;
import static com.zerobase.fitme.type.ErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.CategoryException;
import com.zerobase.fitme.model.RegBrand;
import com.zerobase.fitme.model.UdtBrand;
import com.zerobase.fitme.model.UdtBrand.Request;
import com.zerobase.fitme.model.UdtCategory;
import com.zerobase.fitme.repository.BrandRepository;
import com.zerobase.fitme.type.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        if (brandRepository.findByBrandName(request.getBrandName()).isPresent()) {
            throw new BrandException(ALREADY_EXIST_BRAND_NAME);
        }

        brandRepository.save(Brand.builder()
            .brandName(request.getBrandName())
            .address(request.getAddress())
            .phone(request.getPhone())
            .regDt(LocalDate.now())
            .build());
    }

    /**
     * 브랜드 조회
     * @return
     */
    public List<Brand> read() {
        return brandRepository.findAll();
    }

    /**
     * 브랜드 수정
     * @param request
     * @return Brand
     */
    public Brand patch(UdtBrand.Request request) {
        validationPatch(request);

        Brand brand = brandRepository.findById(request.getId()).orElseThrow(() ->
            new BrandException(BRAND_NOT_FOUND)
        );

        brand.patch(request);

        return brandRepository.save(brand);
    }

    /**
     * 브렌드 삭제
     * @param id
     */
    public void delete(Long id) {
        brandRepository.delete(
            brandRepository.findById(id).orElseThrow(() -> new BrandException(BRAND_NOT_FOUND))
        );
    }

    private void validationPatch(UdtBrand.Request request) {
        if(!StringUtils.hasText(request.getBrandName()) && !StringUtils.hasText(request.getPhone())
            && !StringUtils.hasText(request.getAddress())){
            throw new BrandException(INVALID_REQUEST);
        }
        if(brandRepository.findByBrandName(request.getBrandName()).isPresent()){
            throw new BrandException(ALREADY_EXIST_BRAND_NAME);
        }
    }


}
