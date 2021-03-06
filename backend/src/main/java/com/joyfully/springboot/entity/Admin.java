package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理
 *
 * @author marx
 * @date 2021/08/02
 */
@TableName("admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名字
     */
    private String name;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 权限
     */
    private Boolean power;
    /**
     * 令牌
     */
    @TableField(exist = false)
    private String token;
}
