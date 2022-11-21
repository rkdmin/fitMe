package com.zerobase.fitme.type;

public enum OrderStatus {
    // 결제중("결제중"),(생략)
    준비중("준비중"),
    // 배송중("배송중"),(생략)
    배송완료("배송완료"),
    구매확정("구매확정")
    ;

    private String status;

    OrderStatus(String status){ this.status = status; }

    public static OrderStatus getType(String status){
        for(OrderStatus type: OrderStatus.values()){
            if(status.equals(type.status)){
                return type;
            }
        }
        return null;
    }
}
