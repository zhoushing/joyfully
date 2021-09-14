package com.joyfully.springboot.common;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回给前台的信息包装类
 *
 * @author marx
 * @date 2021/07/31
 */
@Data
@NoArgsConstructor
public class Result<T> {
    /**
     * 操作状态码
     */
    private String code;

    /**
     * 附带消息
     */
    private String msg;

    /**
     * 处理过的数据或者反馈
     */
    private T data;

    public Result(T data) {
        this.data = data;
    }

    /**
     * 操作成功
     *
     * @return {@link Result}
     */
    public static Result success() {
        Result result = new Result();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    /**
     * 成功,打包指定数据
     *
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    /**
     * 错误
     *
     * @param code 错误代码
     * @param msg  提示消息
     * @return {@link Result}
     */
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
