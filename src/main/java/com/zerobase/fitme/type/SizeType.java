package com.zerobase.fitme.type;

public enum SizeType {
    S("s"),
    M("m"),
    L("l"),
    XL("xl"),
    XXL("xxl"),
    FREE("free")
    ;

    private String size;

    SizeType(String size){ this.size = size; }

    public static SizeType getType(String size){
        for(SizeType type: SizeType.values()){
            if(size.equals(type.size)){
                return type;
            }
        }
        return null;
    }
}
