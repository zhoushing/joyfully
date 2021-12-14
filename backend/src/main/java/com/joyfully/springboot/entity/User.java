package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户
 *
 * @author marx
 * @date 2021/07/31
 */
@TableName("user")
@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户密码
     */
    private String pwd;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 性别
     */
    private String sex;
    /**
     * 地址
     */
    private String address;

    /**
     * 用户对应的问题列表
     */
    @TableField(exist = false)
    private List<Question> questionList;

    /**
     * 令牌
     */
    @TableField(exist = false)
    private String token;
}
