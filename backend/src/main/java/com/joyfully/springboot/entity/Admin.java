package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String pwd;
    private Boolean power;
}
