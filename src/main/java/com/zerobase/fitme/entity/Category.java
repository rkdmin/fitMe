package com.zerobase.fitme.entity;

import com.zerobase.fitme.model.UdtCategory.Request;
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
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    private boolean usingYn;

    public void patch(Request request) {
        if(request.getUsingYn() != null){
            this.usingYn = request.getUsingYn();
        }
        if(StringUtils.hasText(request.getCategoryName())){
            this.categoryName = request.getCategoryName();
        }
    }
}