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
    private String name;
    private String pwd;
    private String nickName;
    private LocalDate birthday;
    private String sex;
    private String address;

    @TableField(exist = false)
    private List<Question> questionList;
}
