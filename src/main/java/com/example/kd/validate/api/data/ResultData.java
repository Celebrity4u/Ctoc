package com.example.kd.validate.api.data;

import java.io.Serializable;

public class ResultData<T extends Serializable> extends SimResult {

    public static <T extends Serializable> SimResult<T> errorData(String msg) {
        //DBFactory.closeSqlSession();
        return SimResult.error(msg);
    }
    public static <T extends Serializable> SimResult<T> errorData(String msg, Integer code) {
        //DBFactory.closeSqlSession();
        return SimResult.error(msg,code);
    }
    public static <T extends Serializable> SimResult<T> errorApplicationData(String msg) {
        //DBFactory.closeSqlSession();
        return SimResult.errorApplication(msg);
    }
    public static <T extends Serializable> SimResult<T> errorClick(String msg, T data) {
        //DBFactory.closeSqlSession();
        return SimResult.errorConfclick(msg,data);
    }
    public static <T extends Serializable> SimResult<T> errorApplicationDataInfo(String msg, T data) {
        //DBFactory.closeSqlSession();
        return SimResult.errorApplicationAndData(msg,data);
    }
    public static <T extends Serializable> SimResult<T> errorData(ResultEnum resultEnum) {
        //DBFactory.closeSqlSession();
        return SimResult.error(resultEnum);
    }

    public static <T extends Serializable> SimResult<T> errorData(int code, String msg) {
        //DBFactory.closeSqlSession();
        return SimResult.error(code,msg);
    }

    public static <T extends Serializable> SimResult<T> successData(T data) {
        //DBFactory.closeSqlSession();
        return SimResult.success(data);
    }
    public static <T extends Serializable> SimResult<T> successDataNoClose(T data) {
        ////DBFactory.closeSqlSession();
        return SimResult.success(data);
    }
}
