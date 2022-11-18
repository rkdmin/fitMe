package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.BrandErrorCode.BRAND_NOT_FOUND;
import static com.zerobase.fitme.exception.type.ModelErrorCode.MODEL_NOT_FOUND;
import static com.zerobase.fitme.exception.type.SellerErrorCode.SELLER_NOT_FOUND;

import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.ItemInfo;
import com.zerobase.fitme.entity.Model;
import com.zerobase.fitme.entity.Seller;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.ModelException;
import com.zerobase.fitme.exception.SellerException;
import com.zerobase.fitme.model.RegItem;
import com.zerobase.fitme.repository.ItemRepository;
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
    private final ItemInfoService itemInfoService;

    /**
     * 상품 등록
     * @param request
     */
    public void register(RegItem.Request request) {
        // 브랜드, 판매자, 모델을 모두 가져옴
        Brand brand = brandService.readById(request.getBrandId())
            .orElseThrow(() -> new BrandException(BRAND_NOT_FOUND));
        Seller seller = sellerService.readById(request.getSellerId())
            .orElseThrow(() -> new SellerException(SELLER_NOT_FOUND));
        Model model = modelService.readById(request.getModelId())
            .orElseThrow(() -> new ModelException(MODEL_NOT_FOUND));

        // 할인 계산
        request.setSaleRate(calculateSalePrice(request.getPrice(), request.getSaleRate()));

        // 상세정보 저장
        ItemInfo itemInfo = itemInfoService.register(request.getRegItemInfo());

        // 아이템저장
        Item item = itemRepository.save(
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
                .itemInfo(itemInfo)
                .brand(brand)
                .seller(seller)
                .model(model)
                .build()
        );

        log.info(item.getItemInfo().getColorList().toString());
    }

    private Long calculateSalePrice(Long price, Long saleRate) {
        if(saleRate <= 0){
            return price;
        }
        return price - (price * (saleRate / 100));
    }

}
