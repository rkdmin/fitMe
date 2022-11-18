package com.zerobase.fitme.entity;

import com.zerobase.fitme.model.UdtModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int height;
    private int topSize;
    private int bottomSize;
    private int shoesSize;
    private String modelName;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    public void patch(UdtModel.Request request) {
        if(request.getHeight() != null){
            this.height = request.getHeight();
        }
        if(request.getTopSize() != null){
            this.topSize = request.getTopSize();
        }
        if(request.getBottomSize() != null){
            this.bottomSize = request.getBottomSize();
        }
        if(request.getShoesSize() != null){
            this.shoesSize = request.getShoesSize();
        }
        if(StringUtils.hasText(request.getModelName())){
            this.modelName = request.getModelName();
        }

        this.udtDt = LocalDateTime.now();
    }
}
