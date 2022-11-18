package com.zerobase.fitme.entity;

import com.zerobase.fitme.model.UdtBrand.Request;
import java.time.LocalDate;
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
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brandName;
    private String url;
    private String phone;
    private LocalDate regDt;
    private LocalDate udtDt;

    public void patch(Request request) {
        if(StringUtils.hasText(request.getBrandName())){
            this.brandName = request.getBrandName();
        }
        if(StringUtils.hasText(request.getUrl())){
            this.url = request.getUrl();
        }
        if(StringUtils.hasText(request.getPhone())){
            this.phone = request.getPhone();
        }

        this.udtDt = LocalDate.now();
    }
}
