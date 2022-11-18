package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.BrandErrorCode.BRAND_NOT_FOUND;
import static com.zerobase.fitme.exception.type.ModelErrorCode.MODEL_NOT_FOUND;
import static com.zerobase.fitme.exception.type.SellerErrorCode.SELLER_NOT_FOUND;

import com.zerobase.fitme.entity.Brand;
import com.zerobase.fitme.entity.Category;
import com.zerobase.fitme.entity.Item;
import com.zerobase.fitme.entity.ItemCategory;
import com.zerobase.fitme.entity.Model;
import com.zerobase.fitme.entity.Seller;
import com.zerobase.fitme.exception.BrandException;
import com.zerobase.fitme.exception.ModelException;
import com.zerobase.fitme.exception.SellerException;
import com.zerobase.fitme.dto.ItemDto;
import com.zerobase.fitme.repository.ItemRepository;
import java.time.LocalDateTime;
import java.util.List;
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
    private final CategoryService categoryService;
    private final ItemCategoryService itemCategoryService;

    /**
     * 상품 등록
     * @param request
     */
    public void register(ItemDto.Request request) {
        // 브랜드, 판매자, 모델, 카테고리를 모두 가져옴
        Brand brand = brandService.readById(request.getBrandId())
            .orElseThrow(() -> new BrandException(BRAND_NOT_FOUND));
        Seller seller = sellerService.readById(request.getSellerId())
            .orElseThrow(() -> new SellerException(SELLER_NOT_FOUND));
        Model model = modelService.readById(request.getModelId())
            .orElseThrow(() -> new ModelException(MODEL_NOT_FOUND));
        List<Category> categoryList = categoryService.readByCategoryNameList(request.getCategoryNameList());

        // 할인 계산
        request.setSaleRate(calculateSalePrice(request.getPrice(), request.getSaleRate()));

        // 아이템엔티티생성
        Item item = Item.builder()
            .itemName(request.getItemName())
            .url(request.getUrl())
            .price(request.getPrice())
            .salePrice(request.getSaleRate())
            .saleRate(request.getSaleRate())
            .content(request.getContent())
            .view(0)
            .cnt(request.getCnt())
            .regDt(LocalDateTime.now())
            .brand(brand)
            .seller(seller)
            .model(model)
            .build();

        // 상품상세정보 저장
        item.setItemInfo(itemInfoService.register(request.getRegItemInfo()));

        // 상품-카테고리 정보 저장
        List<ItemCategory> itemCategoryList = itemCategoryService.register(item, categoryList);
        item.setItemCategoryList(itemCategoryList);

        // 마지막으로 아이템 저장
        itemRepository.save(item);
    }

    private Long calculateSalePrice(Long price, Long saleRate) {
        if(saleRate <= 0){
            return price;
        }
        return price - (price * (saleRate / 100));
    }

}
