package com.zerobase.fitme.model;

import lombok.Builder;
import lombok.Data;

public class UdtModel {
    @Data
    @Builder
    public static class Request{
        private Integer height;
        private Integer topSize;
        private Integer bottomSize;
        private Integer shoesSize;
        private String modelName;
    }
}
