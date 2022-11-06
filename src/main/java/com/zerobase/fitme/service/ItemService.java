package com.zerobase.fitme.service;

import static com.zerobase.fitme.type.ErrorCode.*;

import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.Model;
import com.zerobase.fitme.entity.Seller;
import com.zerobase.fitme.exception.ItemException;
import com.zerobase.fitme.model.RegItem;
import com.zerobase.fitme.repository.ItemRepository;
import com.zerobase.fitme.type.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final BrandService brandService;
    private final ModelService modelService;
    private final SellerService sellerService;

    /**
     * 상품 등록
     * @param request
     */
    public void register(RegItem.Request request) {
        // 브랜드, 판매자, 모델을 모두 가져옴
        Brand brand = brandService.readById(request.getBrandId())
            .orElseThrow(() -> new ItemException(BRAND_NOT_FOUND));
        Seller seller = sellerService.readById(request.getSellerId())
            .orElseThrow(() -> new ItemException(SELLER_NOT_FOUND));
        Model model = modelService.readById(request.getModelId())
            .orElseThrow(() -> new ItemException(MODEL_NOT_FOUND));

        // 할인 계산
        request.setSaleRate(calculate(request.getPrice(), request.getSaleRate()));

        // 상세정보 저장
        // 클라이언트에서 받아온 String 데이터를 enum 데이터 리스트로 어떻게 바꾸는지 모르겠습니다.

        itemRepository.save(
            Item.builder()
                .itemName(request.getItemName())
                .url(request.getUrl())
                .price(request.getPrice())
                .salePrice(request.getSaleRate())
                .saleRate(request.getSaleRate())
                .content(request.getContent())
                .view(0)
                .cnt(request.getCnt())
                .regDt(LocalDateTime.now())
                .itemInfo(null)// enum 이슈..
                .brand(brand)
                .seller(seller)
                .model(model)
                .build()
        );
    }

    private Long calculate(Long price, Long saleRate) {
        if(saleRate <= 0){
            return price;
        }
        return price * (saleRate / 100);
    }

}
