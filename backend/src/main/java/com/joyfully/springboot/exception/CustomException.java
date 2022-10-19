package com.joyfully.springboot.exception;

import com.joyfully.springboot.enums.ExceptionEnum;
import com.joyfully.springboot.enums.HttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author marx
 * @date 2021/11/11
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    /**
     * 错误代码
     */
    String code;
    /**
     * 附带消息
     */
    String msg;

    public CustomException() {
        ExceptionEnum exceptionEnum = ExceptionEnum.exceptionMap.get(this.getClass().getSimpleName());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    public CustomException(String msg) {
        ExceptionEnum exceptionEnum = ExceptionEnum.exceptionMap.get(this.getClass().getSimpleName());
        this.code = exceptionEnum.getCode();
        this.msg = msg;
    }

    public CustomException(HttpCodeEnum httpCodeEnum) {
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
