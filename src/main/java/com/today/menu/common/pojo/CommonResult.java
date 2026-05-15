package com.today.menu.common.pojo;

import lombok.Data;
import java.io.Serializable;

@Data
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private T data;
    private String message;

    public CommonResult() {
    }

    public CommonResult(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(0, data, "success");
    }

    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(0, data, message);
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        return new CommonResult<>(code, null, message);
    }

    public static <T> CommonResult<T> error(String message) {
        return new CommonResult<>(500001, null, message);
    }
}