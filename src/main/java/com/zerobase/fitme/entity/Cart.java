package com.zerobase.fitme.entity;

import com.zerobase.fitme.dto.CartDto;
import com.zerobase.fitme.model.UdtCategory.Request;
import com.zerobase.fitme.type.ColorType;
import com.zerobase.fitme.type.SizeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private ColorType color;
    private SizeType size;

    public void patch(CartDto.RequestPatch request) {
        if(StringUtils.hasText(request.getColor())){
            this.color = ColorType.getType(request.getColor());
        }
        if(StringUtils.hasText(request.getSize())){
            this.size = SizeType.getType(request.getSize());
        }
    }
}
