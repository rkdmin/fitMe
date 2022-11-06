package com.zerobase.fitme.entity;

import com.zerobase.fitme.model.UdtBrand.Request;
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

    public void patch(Request request) {
//        if(StringUtils.hasText(request.getBrandName())){
//            this.brandName = request.getBrandName();
//        }
//        if(StringUtils.hasText(request.getAddress())){
//            this.address = request.getAddress();
//        }
//        if(StringUtils.hasText(request.getPhone())){
//            this.phone = request.getPhone();
//        }
//
//        this.udtDt = LocalDate.now();
    }
}
