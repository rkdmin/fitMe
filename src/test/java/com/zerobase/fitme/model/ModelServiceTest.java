package com.zerobase.fitme.model;

import static com.zerobase.fitme.exception.type.ModelErrorCode.INVALID_REQUEST;
import static com.zerobase.fitme.exception.type.ModelErrorCode.MODEL_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zerobase.fitme.entity.Model;
import com.zerobase.fitme.exception.ModelException;
import com.zerobase.fitme.repository.ModelRepository;
import com.zerobase.fitme.service.ModelService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ModelServiceTest {

    @Mock
    private ModelRepository modelRepository;
    @InjectMocks
    private ModelService modelService;

    @Test// 로직이 없음
    void 모델_등록_성공() {
        // given

        // when

        // then
    }

    @Test// 로직이 없음
    void 모델_조회_성공() {
        // given

        // when

        // then
    }

    @Test
    void 모델_수정_실패_요청값이없음() {
        // given
        UdtModel.Request request = UdtModel.Request.builder().build();// 값이 하나도 없음
        Long modelId = 1L;

        // when
        ModelException exception = assertThrows(ModelException.class,
            () -> modelService.patch(request, modelId));

        // then
        assertEquals(INVALID_REQUEST, exception.getErrorCode());
    }

    @Test
    void 모델_수정_실패_모델이없음() {
        // given
        UdtModel.Request request = UdtModel.Request.builder()
            .modelName("홍길동")
            .build();
        Long modelId = 1L;

        given(modelRepository.findById(anyLong()))
            .willReturn(Optional.empty());

        // when
        ModelException exception = assertThrows(ModelException.class,
            () -> modelService.patch(request, modelId));

        // then
        assertEquals(MODEL_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 모델_수정_성공() {
        // given
        String requestModelName = "짱구";// 변경할 모델명
        String modelName = "홍길동";// 원래 모델명
        UdtModel.Request request = UdtModel.Request.builder()
            .modelName(requestModelName)
            .build();
        Long modelId = 1L;
        Model.builder().build();

        given(modelRepository.findById(anyLong()))
            .willReturn(Optional.of(Model.builder().modelName(modelName).build()));

        ArgumentCaptor<Model> captor = ArgumentCaptor.forClass(Model.class);

        // when
        modelService.patch(request, modelId);

        // then
        verify(modelRepository, times(1)).save(captor.capture());
        assertEquals(requestModelName, captor.getValue().getModelName());
    }
}