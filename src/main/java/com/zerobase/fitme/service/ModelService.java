package com.zerobase.fitme.service;

import com.zerobase.fitme.entity.Model;
import com.zerobase.fitme.model.RegModel;
import com.zerobase.fitme.repository.ModelRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelService {
    private final ModelRepository modelRepository;

    /**
     * 모델 등록
     * @param request
     */
    public void register(RegModel.Request request) {
        modelRepository.save(Model.builder()
                .height(request.getHeight())
                .topSize(request.getTopSize())
                .bottomSize(request.getBottomSize())
                .shoesSize(request.getShoesSize())
                .modelName(request.getModelName())
                .regDt(LocalDateTime.now())
                .build());
    }

//    /**
//     * 브랜드 조회
//     * @return
//     */
//    public List<Brand> read() {
//        return brandRepository.findAll();
//    }
//
//    /**
//     * 브랜드 수정
//     * @param request
//     * @return Brand
//     */
//    public Brand patch(Request request) {
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
//    private void validationPatch(Request request) {
//        if(!StringUtils.hasText(request.getBrandName()) && !StringUtils.hasText(request.getPhone())
//            && !StringUtils.hasText(request.getAddress())){
//            throw new BrandException(INVALID_REQUEST);
//        }
//        if(brandRepository.findByBrandName(request.getBrandName()).isPresent()){
//            throw new BrandException(ALREADY_EXIST_BRAND_NAME);
//        }
//    }


}
