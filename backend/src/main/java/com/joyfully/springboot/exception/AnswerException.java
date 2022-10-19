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
public class AnswerException extends CustomException{
    public AnswerException() {
        super();
    }

    public AnswerException(String msg) {
        super(msg);
    }

    public AnswerException(String code, String msg) {
        super(code, msg);
    }

    public AnswerException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum);
    }
}
