package com.today.menu.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final Integer code;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message) {
        super(message);
        this.code = ErrorCodeConstants.INTERNAL_ERROR;
    }
}