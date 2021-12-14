package com.joyfully.springboot.exception;

/**
 * 自定义异常
 *
 * @author marx
 * @date 2021/11/11
 */
public class CustomException extends RuntimeException {
    /**
     * 错误代码
     */
    private String code;
    /**
     * 附带消息
     */
    private String msg;

    public CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
