package com.joyfully.springboot.exception;

import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.enums.HttpCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理程序
 *
 * @author marx
 * @date 2022/01/12
 */
@ControllerAdvice(basePackages = "com.joyfully.springboot.controller")
public class GlobalExceptionHandler {
    /**
     * 日志记录器
     */
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义异常处理
     *
     * @param request 请求
     * @param e       e
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result<?> custom(HttpServletRequest request, CustomException e) {
        log.error("源url为 " + request.getRequestURI());
        log.error("异常类型为 " + e.getClass().getSimpleName());
        log.error("异常附带消息为 " + e.getMsg());
        log.warn(e.toString());
        return Result.error(e.getCode(), e.getMsg());
    }

    /**
     * 非系统定义异常处理
     *
     * @param request 请求
     * @param e       e
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> error(HttpServletRequest request, Exception e) {
        log.error("源url为 " + request.getRequestURI());
        log.error("异常类型为 " + e.getClass().getSimpleName());
        log.error("异常附带消息为 " + e.getMessage());
        log.error(e.toString());
        return Result.error(HttpCodeEnum.SYSTEM_ERROR);
    }
}
