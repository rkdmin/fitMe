package com.zerobase.fitme.model;

import lombok.Builder;
import lombok.Data;

public class UdtBrand {
    @Data
    @Builder
    public static class Request{
        private Long id;
        private String brandName;
        private String url;
        private String phone;
    }
}
