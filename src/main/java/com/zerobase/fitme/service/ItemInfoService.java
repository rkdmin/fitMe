package com.zerobase.fitme.service;

import com.zerobase.fitme.entity.ItemInfo;
import com.zerobase.fitme.model.RegItemInfo;
import com.zerobase.fitme.repository.ItemInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemInfoService {
    private final ItemInfoRepository itemInfoRepository;

    /**
     * 상품 상세정보 등록
     * @param request
     */
    public void register(RegItemInfo.Request request) {
        itemInfoRepository.save(
            ItemInfo.builder()
                .material(request.getMaterial())
                .colorList(request.getColorList())
                .sizeList(request.getSizeList())
                .build());
    }
}
