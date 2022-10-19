package com.joyfully.springboot.common;

import cn.hutool.http.HttpStatus;
import com.joyfully.springboot.enums.ExceptionEnum;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.exception.CustomException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Result", description = "操作结果")
public class Result<T> {
    /**
     * 操作状态码
     */
    @ApiModelProperty(value = "操作状态码", dataType = "String")
    private String code;

    /**
     * 附带消息
     */
    @ApiModelProperty(value = "附带消息", dataType = "String")
    private String msg;

    /**
     * 处理过的数据或者反馈
     */
    @ApiModelProperty(value = "处理过的数据或者反馈", dataType = "Object")
    private T data;

    public Result(T data) {
        this.data = data;
    }

    public void setStatus(HttpCodeEnum httpCodeEnum) {
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    /**
     * 操作成功
     *
     * @return {@link Result}
     */
    public static Result success() {
        Result result = new Result();
        result.setStatus(HttpCodeEnum.OK);
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
        result.setStatus(HttpCodeEnum.OK);
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

    /**
     * 错误
     *
     * @param customException 自定义异常
     * @return {@link Result}
     */
    public static Result error(CustomException customException) {
        Result result = new Result();
        result.setCode(customException.getCode());
        result.setMsg(customException.getMsg());
        return result;
    }

    /**
     * 错误
     *
     * @param httpCodeEnum http代码枚举
     * @return {@link Result}
     */
    public static Result error(HttpCodeEnum httpCodeEnum) {
        Result result = new Result();
        result.setCode(httpCodeEnum.getCode());
        result.setMsg(httpCodeEnum.getMsg());
        return result;
    }
}
