package com.zerobase.fitme.service;

import static com.zerobase.fitme.exception.type.ModelErrorCode.INVALID_REQUEST;
import static com.zerobase.fitme.exception.type.ModelErrorCode.MODEL_NOT_FOUND;

import com.zerobase.fitme.dto.ModelDto;
import com.zerobase.fitme.entity.Model;
import com.zerobase.fitme.exception.ModelException;
import com.zerobase.fitme.model.UdtModel;
import com.zerobase.fitme.repository.ModelRepository;
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
public class ModelService {
    private final ModelRepository modelRepository;

    /**
     * 모델 등록
     * @param request
     */
    public void register(ModelDto.Request request) {
        modelRepository.save(Model.builder()
                .height(request.getHeight())
                .topSize(request.getTopSize())
                .bottomSize(request.getBottomSize())
                .shoesSize(request.getShoesSize())
                .modelName(request.getModelName())
                .regDt(LocalDateTime.now())
                .build());
    }

    /**
     * 모델 엔티티로 바로 등록
     * @param model
     */
    public void registerByEntity(Model model) {
        modelRepository.save(model);
    }

    /**
     * 모델 조회
     * @return
     */
    public List<Model> read() {
        return modelRepository.findAll();
    }

    /**
     * 모델 아이디로 조회
     * @return
     */
    public Optional<Model> readById(Long id) {
        return modelRepository.findById(id);
    }

    /**
     * 모델 수정
     * @param request
     * @return Model
     */
    public Model patch(UdtModel.Request request, Long id) {
        validationPatch(request);

        Model model = modelRepository.findById(id).orElseThrow(() ->
            new ModelException(MODEL_NOT_FOUND)
        );

        model.setId(id);
        model.patch(request);

        return modelRepository.save(model);
    }

    /**
     * 모델 삭제
     * @param id
     */
    public void delete(Long id) {
        modelRepository.delete(
            modelRepository.findById(id).orElseThrow(() -> new ModelException(MODEL_NOT_FOUND))
        );
    }

    private void validationPatch(UdtModel.Request request) {
        // 값이 하나도 없는 경우
        if(request.getHeight() == null && request.getTopSize() == null && request.getBottomSize() == null &&
            request.getShoesSize() == null && !StringUtils.hasText(request.getModelName())){
            throw new ModelException(INVALID_REQUEST);
        }
    }
}
