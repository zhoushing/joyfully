package com.joyfully.springboot.exception;

import com.joyfully.springboot.enums.HttpCodeEnum;
import lombok.Getter;

/**
 * 回答异常
 *
 * @author marx
 * @date 2022/01/30
 */
@Getter
public class MessageException extends CustomException{
    public MessageException() {
        super();
    }

    public MessageException(String msg) {
        super(msg);
    }

    public MessageException(String code, String msg) {
        super(code, msg);
    }

    public MessageException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum);
    }
}
