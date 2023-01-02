package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.BrandErrorCode.*;
import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.dto.BrandDto;
import com.zerobase.fitme.model.UdtBrand;
import com.zerobase.fitme.repository.BrandRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;

    /**
     * 브랜드 등록
     * @param request
     */
    public void register(BrandDto.Request request) {
        if (brandRepository.findByBrandName(request.getBrandName()).isPresent()) {
            throw new BrandException(ALREADY_EXIST_BRAND_NAME);
        }

        brandRepository.save(Brand.builder()
            .brandName(request.getBrandName())
            .url(request.getUrl())
            .phone(request.getPhone())
            .regDt(LocalDate.now())
            .build());
    }

    /**
     * 브랜드 엔티티로 바로 등록
     * @param brand
     */
    public void register(Brand brand) {
        brandRepository.save(brand);
    }

    /**
     * 브랜드 조회
     * @return
     */
    public List<Brand> read() {
        return brandRepository.findAll();
    }

    /**
     * 브랜드 아이디로 조회
     * @return
     */
    public Optional<Brand> readById(Long id) { return brandRepository.findById(id); }

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
        // 수정값이 없음
        if(!StringUtils.hasText(request.getBrandName()) && !StringUtils.hasText(request.getPhone())
            && !StringUtils.hasText(request.getUrl())){
            throw new BrandException(INVALID_REQUEST);
        }

        // 이미 존재하는 브랜드 이름
        if(brandRepository.findByBrandName(request.getBrandName()).isPresent()){
            throw new BrandException(ALREADY_EXIST_BRAND_NAME);
        }
    }


}
