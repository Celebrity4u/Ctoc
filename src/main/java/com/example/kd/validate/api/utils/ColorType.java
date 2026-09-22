package com.example.kd.validate.api.utils;

import lombok.Getter;

/**
 * 颜色值
 * @author zl
 * Created by zl on 2022/35/30.
 */
@Getter
public enum ColorType {

    RED(0, "红"),
    GREEN(1, "绿"),
    YELLOW(2, "黄"),
    BLUE(3, "蓝"),
    PURPLE(4, "紫"),
    OTHER(5, "深绿");

    private final int code;
    private final String msg;

    ColorType(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
