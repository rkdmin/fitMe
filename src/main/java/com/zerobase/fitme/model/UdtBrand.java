package com.zerobase.fitme.model;

import lombok.Data;

public class UdtBrand {
    @Data
    public static class Request{
        private Long id;
        private String brandName;
        private String address;
        private String phone;
    }
}
