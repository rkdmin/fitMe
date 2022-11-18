package com.zerobase.fitme.type;

public enum ColorType {
    BLACK("black"),
    WHITE("white"),
    YELLOW("yellow"),
    GREEN("green"),
    RED("red"),
    GRAY("gray")
    ;

    private String color;

    ColorType(String color){
        this.color = color;
    }

    public static ColorType getType(String color){
        for(ColorType type: ColorType.values()){
            if(color.equals(type.color)){
                return type;
            }
        }
        return null;
    }
}
