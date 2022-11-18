package com.zerobase.fitme.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private String url;
    private long price;
    private long salePrice;
    private long saleRate;
    private String content;
    private long view;
    private long cnt;

    private LocalDateTime regDt;
    private LocalDateTime uptDt;

    // 일대일 매핑
    @OneToOne
    @JoinColumn(name = "item_info_id")
    private ItemInfo itemInfo;

    // 다대일 매핑
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;
}
