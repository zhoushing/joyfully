package com.joyfully.springboot.exception;

import com.joyfully.springboot.enums.HttpCodeEnum;

/**
 * 异常问题
 *
 * @author marx
 * @date 2022/01/12
 */
public class QuestionException extends CustomException{
    public QuestionException() {
        super();
    }

    public QuestionException(String msg) {
        super(msg);
    }

    public QuestionException(String code, String msg) {
        super(code, msg);
    }

    public QuestionException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum);
    }
}
