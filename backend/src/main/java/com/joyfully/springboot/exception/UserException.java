package com.joyfully.springboot.exception;

import com.joyfully.springboot.enums.HttpCodeEnum;

/**
 * 用户异常
 *
 * @author marx
 * @date 2022/01/11
 */
public class UserException extends CustomException{
    public UserException() {
        super();
    }

    public UserException(String msg) {
        super(msg);
    }

    public UserException(String code, String msg) {
        super(code, msg);
    }

    public UserException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum);
    }
}
