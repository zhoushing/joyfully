package com.joyfully.springboot.exception;

import com.joyfully.springboot.enums.HttpCodeEnum;
import lombok.Getter;

/**
 * 公告异常
 *
 * @author marx
 * @date 2022/04/22
 */
@Getter
public class NoticeException extends CustomException{
    public NoticeException() {
        super();
    }

    public NoticeException(String msg) {
        super(msg);
    }

    public NoticeException(String code, String msg) {
        super(code, msg);
    }

    public NoticeException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum);
    }
}
