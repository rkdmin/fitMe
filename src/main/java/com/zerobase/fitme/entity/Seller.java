package com.zerobase.fitme.entity;

import com.zerobase.fitme.model.UdtBrand.Request;
import com.zerobase.fitme.model.UdtSeller;
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

    public void patch(UdtSeller.Request request) {
        if(StringUtils.hasText(request.getCompanyName())){
            this.companyName = request.getCompanyName();
        }
        if(StringUtils.hasText(request.getSellerName())){
            this.sellerName = request.getSellerName();
        }
        if(StringUtils.hasText(request.getAddress())){
            this.address = request.getAddress();
        }
        if(StringUtils.hasText(request.getPhone())){
            this.phone = request.getPhone();
        }
        if(StringUtils.hasText(request.getBusinessNumber())){
            this.businessNumber = request.getBusinessNumber();
        }
        if(StringUtils.hasText(request.getEmail())){
            this.email = request.getEmail();
        }

        this.udtDt = LocalDateTime.now();
    }
}
