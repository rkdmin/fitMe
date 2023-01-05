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

    public void patch(ColorType color, SizeType size) {
        if(color != null){
            this.color = color;
        }
        if(size != null){
            this.size = size;
        }
    }
}
