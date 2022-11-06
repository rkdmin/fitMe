package com.zerobase.fitme.service;

import static com.zerobase.fitme.type.ErrorCode.*;
import static com.zerobase.fitme.type.ErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.entity.Seller;
import com.zerobase.fitme.exception.SellerException;
import com.zerobase.fitme.model.RegSeller;
import com.zerobase.fitme.model.UdtSeller;
import com.zerobase.fitme.repository.SellerRepository;
import com.zerobase.fitme.type.ErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * 판매자 수정
     * @param request
     * @return Seller
     */
    public Seller patch(UdtSeller.Request request, Long id) {
        validationPatch(request);

        Seller seller = sellerRepository.findById(id).orElseThrow(() ->
            new SellerException(INVALID_REQUEST)
        );

        seller.setId(id);
        seller.patch(request);

        return sellerRepository.save(seller);
    }

    /**
     * 판매자 삭제
     * @param id
     */
    public void delete(Long id) {
        sellerRepository.delete(
            sellerRepository.findById(id).orElseThrow(() -> new SellerException(SELLER_NOT_FOUND))
        );
    }

    private void validationPatch(UdtSeller.Request request) {
        if(!StringUtils.hasText(request.getCompanyName()) && !StringUtils.hasText(request.getSellerName())
            && !StringUtils.hasText(request.getAddress()) && !StringUtils.hasText(request.getPhone())
            && !StringUtils.hasText(request.getBusinessNumber()) && !StringUtils.hasText(request.getEmail())){
            throw new SellerException(INVALID_REQUEST);
        }
    }


}
