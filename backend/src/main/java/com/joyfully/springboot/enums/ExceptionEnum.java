package com.joyfully.springboot.enums;

import com.joyfully.springboot.entity.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

/**
 * 异常枚举
 *
 * @author marx
 * @date 2022/03/22
 */
@Getter
@AllArgsConstructor
public enum  ExceptionEnum {
    /**
     * 自定义
     */
    CUSTOM("-1", "未定义异常"),
    /**
     * 回答
     */
    ANSWER("-1", "答案操作异常"),
    /**
     * 评价
     */
    EVALUATION("-1", "评价操作异常"),
    /**
     * 文件
     */
    FILE("-1", "文件操作异常"),
    /**
     * 问题
     */
    QUESTION("-1", "问题操作异常"),
    /**
     * 用户
     */
    USER("-1", "用户操作异常");

    /**
     * 代码
     */
    private final String code;
    /**
     * 消息
     */
    private final String msg;

    /**
     * 异常map
     */
    public static HashMap<String, ExceptionEnum> exceptionMap = new HashMap<String, ExceptionEnum>(){{
        put("CustomException", CUSTOM);
        put("AnswerException", ANSWER);
        put("EvaluationException", EVALUATION);
        put("FileException", FILE);
        put("QuestionException", QUESTION);
        put("UserException", USER);
    }};
}
