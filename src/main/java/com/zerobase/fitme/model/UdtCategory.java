package com.zerobase.fitme.model;

import lombok.Builder;
import lombok.Data;

public class UdtCategory {
    @Data
    @Builder
    public static class Request{
        Long id;
        private String categoryName;
    }
}
