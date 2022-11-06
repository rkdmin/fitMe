package com.zerobase.fitme.entity;

import com.zerobase.fitme.model.UdtBrand.Request;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String sellerName;
    private String address;
    private String phone;
    private String businessNumber;
    private String email;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    public void patch(Request request) {
//        if(StringUtils.hasText(request.getBrandName())){
//            this.brandName = request.getBrandName();
//        }
//        if(StringUtils.hasText(request.getAddress())){
//            this.url = request.getAddress();
//        }
//        if(StringUtils.hasText(request.getPhone())){
//            this.phone = request.getPhone();
//        }
//
//        this.udtDt = LocalDate.now();
    }
}
