package com.joyfully.springboot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * http状态码码枚举
 * 本Enum类参考Alibaba规范的错误码
 *
 * @author marx
 * @date 2022/03/22
 */
@Getter
@AllArgsConstructor
public enum HttpCodeEnum {
    /**
     * 正常
     */
    OK("00000", "操作成功"),

    /**
     * 用户注册错误
     */
    USER_REGISTER_ERROR("A0100", "用户注册失败"),
    /**
     * 用户名已经存在
     */
    USERNAME_ALREADY_EXISTS("A0111", "用户名已存在"),
    /**
     * 用户名包含敏感词
     */
    USERNAME_CONTAINS_SENSITIVE_WORDS("A0112", "用户名包含敏感词"),
    /**
     * 用户名包含特殊字符
     */
    USERNAME_CONTAINS_SPECIAL_CHARACTERS("A0113", "用户名包含特殊字符"),

    /**
     * 用户登录异常
     */
    USER_LOGIN_EXCEPTION("A0200", "用户登录异常"),
    /**
     * 用户账户不找到
     */
    USER_ACCOUNT_NOT_FIND("A0201", "用户账户不存在"),
    /**
     * 用户账户注销
     */
    USER_ACCOUNT_LOGOUT("A0203", "用户账户已注销"),

    /**
     * 用户密码错误
     */
    USER_PASSWORD_ERROR("A0210", "用户密码错误"),

    /**
     * 用户登录到期
     */
    USER_LOGIN_EXPIRE("A0230", "用户登录过期"),

    /**
     * 用户请求参数错误
     */
    USER_REQUEST_PARAM_ERROR("A0400", "用户请求参数错误"),
    /**
     * 无效用户输入
     */
    INVALID_USER_INPUT("A0402", "无效的用户输入"),
    /**
     * 用户昵称重复
     */
    USER_NICKNAME_REPEAT("A0403", "用户昵称重复"),
    /**
     * 要搜索的昵称为空
     */
    THE_NICKNAME_EMPTY("A0403", "要搜索的昵称为空"),

    /**
     * 用户输入内容非法
     */
    USER_INPUT_CONTENT_ILLEGAL("A0430", "用户输入内容非法"),

    /**
     * 用户操作异常
     */
    USER_OPERATION_EXCEPTION("A0440", "用户操作异常"),

    /**
     * 用户请求服务异常
     */
    USER_REQUEST_SERVICE_EXCEPTION("A0500", "用户请求服务异常"),
    /**
     * websocket连接异常
     */
    WEBSOCKET_CONNECT_EXCEPTION("A0504", "websocket连接异常"),
    /**
     * 用户重复请求
     */
    USER_REPEAT_REQUEST("A0506", "用户重复请求"),

    /**
     * 用户上传异常
     */
    USER_UPLOAD_EXCEPTION("A0700", "用户上传异常"),

    /**
     * 答案操作异常
     */
    ANSWER_OPERATION_EXCEPTION("A1100", "答案操作异常"),
    /**
     * 答案未关联问题
     */
    ANSWER_UNRELATED_QUESTION("A1101", "答案未关联问题"),
    /**
     * 答案未关联用户
     */
    ANSWER_UNRELATED_USER("A1102", "答案未关联用户"),
    /**
     * 答案内容未填写
     */
    ANSWER_NOT_FILL("A1103", "答案内容未填写"),

    /**
     * 评价操作异常
     */
    EVALUATION_OPERATION_EXCEPTION("A1200", "评价操作异常"),
    /**
     * 评价目标id异常
     */
    EVALUATION_TARGET_ID_EXCEPTION("A1201", "评价目标id异常"),
    /**
     * 评价目标不存在
     */
    EVALUATION_TARGET_NOT_EXISTS("A1203", "评价目标不存在"),
    /**
     * 评价目标为空
     */
    EVALUATION_TARGET_IS_EMPTY("A1204", "评价目标为空"),
    /**
     * 评价目标暂不支持
     */
    EVALUATION_TARGET_NOT_SUPPORT("A1205", "评价目标暂不支持"),
    /**
     * 当前目标已经评价过了
     */
    ALREADY_EVALUATED("A1206", "当前目标已经评价过了"),
    /**
     * 举报重复
     */
    REPORT_REPEAT("A1207", "举报正在受理中，请勿重复举报"),

    /**
     * 问题操作异常
     */
    QUESTION_OPERATION_EXCEPTION("A1300", "问题操作异常"),
    /**
     * 目标问题不存在
     */
    QUESTION_NOT_FIND("A1301", "目标问题不存在"),
    /**
     * 关注问题失败
     */
    ATTENTION_QUESTION_FAILED("A1302", "关注问题失败"),
    /**
     * 重复关注问题
     */
    REPEAT_ATTENTION_QUESTION("A1303", "重复关注问题"),
    /**
     * 用户id格式错误
     */
    USER_ID_FORMAT_ERROR("A1304", "用户id格式错误"),
    /**
     * 问题类别重复
     */
    QUESTION_CATEGORY_REPEAT("A1305", "问题类别重复"),
    /**
     * 问题类别列表异常
     */
    QUESTION_CATEGORY_LIST_EXCEPTION("A1306", "问题类别列表异常"),


    /**
     * 文件操作异常
     */
    FILE_OPERATION_EXCEPTION("A1400", "文件操作异常"),
    /**
     * 目标文件不存在
     */
    FILE_NOT_EXIST("A1401", "目标文件不存在"),
    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_FAILED("A1402", "文件下载失败"),
    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAILED("A1403", "文件上传失败"),
    /**
     * 文件大小超过限制
     */
    FILE_SIZE_EXCEEDS_LIMIT("A1404", "文件大小超过限制"),
    /**
     * 上传文件异常
     */
    UPLOADED_FILE_EXCEPTION("A1405", "上传文件异常"),

    /**
     * 公告操作异常
     */
    NOTICE_OPERATION_EXCEPTION("A1500", "公告操作异常"),
    /**
     * 公告重复置顶
     */
    NOTICE_REPEAT_TOP("A1501", "公告重复置顶"),

    /**
     * 消息操作异常
     */
    MESSAGE_OPERATION_EXCEPTION("A1600", "消息操作异常"),
    /**
     * 消息内容为空
     */
    MESSAGE_CONTENT_IS_NULL("A1601", "消息内容为空"),


    /**
     * 系统错误
     */
    SYSTEM_ERROR("B0001", "系统错误"),

    /**
     * 系统超时
     */
    SYSTEM_TIMEOUT("B0100", "系统超时"),


    /**
     * 第三方服务错误
     */
    THIRD_PARTY_SERVICE_ERROR("C0001", "第三方服务错误"),

    /**
     * 数据库服务超时
     */
    DB_SERVICE_TIMEOUT("C0250", "数据库服务超时"),

    /**
     * 数据库服务错误
     */
    DB_SERVICE_ERROR("C0300", "数据库服务错误"),

    /**
     * 数据库表不存在
     */
    DB_TABLE_NOT_EXISTS("C0311", "数据库表不存在"),
    /**
     * 数据库字段不存在
     */
    DB_FIELD_NOT_EXISTS("C0312", "数据库字段不存在"),


    /**
     * 数据库操作错误
     */
    DB_OPERATION_ERROR("C0350", "数据库操作错误"),
    /**
     * 数据库插入错误
     */
    DB_INSERT_ERROR("C0351", "数据库插入错误"),
    /**
     * 数据库删除错误
     */
    DB_DELETE_ERROR("C0351", "数据库删除错误"),
    /**
     * 数据库更新错误
     */
    DB_UPDATE_ERROR("C0351", "数据库更新错误"),
    /**
     * 数据库查询错误
     */
    DB_SELECT_ERROR("C0351", "数据库查询错误"),
    ;

    /**
     * 状态码
     */
    private final String code;

    /**
     * 消息
     */
    private final String msg;
}
