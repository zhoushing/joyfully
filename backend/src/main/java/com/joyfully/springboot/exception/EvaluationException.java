package com.joyfully.springboot.exception;

import com.joyfully.springboot.enums.HttpCodeEnum;

/**
 * 评价异常
 *
 * @author marx
 * @date 2022/03/20
 */
public class EvaluationException extends CustomException{
    public EvaluationException() {
        super();
    }

    public EvaluationException(String msg) {
        super(msg);
    }

    public EvaluationException(String code, String msg) {
        super(code, msg);
    }

    public EvaluationException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum);
    }
}
