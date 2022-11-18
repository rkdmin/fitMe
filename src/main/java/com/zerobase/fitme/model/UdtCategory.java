package com.zerobase.fitme.model;

import lombok.Data;

public class UdtCategory {
    @Data
    public static class Request{
        Long id;
        private String categoryName;
        private Boolean usingYn;
    }
}
