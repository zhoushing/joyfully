package com.joyfully.springboot.exception;

import com.joyfully.springboot.enums.HttpCodeEnum;

/**
 * 文件异常
 *
 * @author marx
 * @date 2022/02/26
 */
public class FileException extends CustomException{
    public FileException() {
        super();
    }

    public FileException(String msg) {
        super(msg);
    }

    public FileException(String code, String msg) {
        super(code, msg);
    }

    public FileException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum);
    }
}
