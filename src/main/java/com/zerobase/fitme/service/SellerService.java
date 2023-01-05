package com.zerobase.fitme.service;


import static com.zerobase.fitme.exception.type.SellerErrorCode.INVALID_REQUEST;
import static com.zerobase.fitme.exception.type.SellerErrorCode.SELLER_NOT_FOUND;

import com.zerobase.fitme.entity.Seller;
import com.zerobase.fitme.exception.SellerException;
import com.zerobase.fitme.dto.SellerDto;
import com.zerobase.fitme.model.UdtSeller;
import com.zerobase.fitme.repository.SellerRepository;
import java.time.LocalDateTime;
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
public class SellerService {
    private final SellerRepository sellerRepository;

    /**
     * 판매자 등록
     * @param request
     */
    public void register(SellerDto.Request request) {
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
     * 판매자 엔티티로 바로 등록
     * @param seller
     */
    public void register(Seller seller) {
        sellerRepository.save(seller);
    }

    /**
     * 판매자 조회
     * @return
     */
    public List<Seller> read() {
        return sellerRepository.findAll();
    }

    /**
     * 판매자 아이디로 조회
     * @return
     */
    public Optional<Seller> readById(Long id) {
        return sellerRepository.findById(id);
    }

    /**
     * 판매자 수정
     * @param request
     * @return Seller
     */
    public Seller patch(UdtSeller.Request request, Long id) {
        validationPatch(request);

        Seller seller = sellerRepository.findById(id).orElseThrow(() ->
            new SellerException(SELLER_NOT_FOUND)
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
