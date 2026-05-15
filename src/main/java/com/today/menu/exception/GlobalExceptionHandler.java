package com.today.menu.exception;

import com.today.menu.common.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public CommonResult<Void> handleServiceException(ServiceException e) {
        log.error("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public CommonResult<Void> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常: message={}", e.getMessage(), e);
        return CommonResult.error(ErrorCodeConstants.INTERNAL_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数校验异常: {}", message);
        return CommonResult.error(ErrorCodeConstants.PARAM_ERROR, message);
    }

    @ExceptionHandler(BindException.class)
    public CommonResult<Void> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数绑定异常: {}", message);
        return CommonResult.error(ErrorCodeConstants.PARAM_ERROR, message);
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<Void> handleException(Exception e) {
        log.error("系统异常: message={}", e.getMessage(), e);
        return CommonResult.error(ErrorCodeConstants.INTERNAL_ERROR, "系统异常");
    }
}