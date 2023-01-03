package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.ItemInfoErrorCode.INVALID_REQUEST;

import com.zerobase.fitme.entity.ItemInfo;
import com.zerobase.fitme.exception.ItemInfoException;
import com.zerobase.fitme.dto.ItemInfoDto;
import com.zerobase.fitme.repository.ItemInfoRepository;
import com.zerobase.fitme.type.ColorType;
import com.zerobase.fitme.type.SizeType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
    public ItemInfo register(ItemInfoDto request) {
        // 색상 불러오기
        List<ColorType> colorTypeList = new ArrayList<>();
        for(String color: request.getColorList()){
            ColorType type = ColorType.getType(color);
            if(ObjectUtils.isEmpty(type)){
                throw new ItemInfoException(INVALID_REQUEST);
            }
            colorTypeList.add(type);
        }
        // 사이즈 불러오기
        List<SizeType> sizeTypeList = new ArrayList<>();
        for(String size: request.getSizeList()){
            SizeType type = SizeType.getType(size);
            if(ObjectUtils.isEmpty(type)){
                throw new ItemInfoException(INVALID_REQUEST);
            }
            sizeTypeList.add(type);
        }

        return itemInfoRepository.save(
                ItemInfo.builder()
                    .material(request.getMaterial())
                    .colorList(colorTypeList)
                    .sizeList(sizeTypeList)
                    .build());
    }
}
