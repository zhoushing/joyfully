package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joyfully.springboot.controller.dto.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * 用户
 *
 * @author marx
 * @date 2021/07/31
 */
@TableName("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "User", description = "用户")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID", dataType = "Integer")
    private Integer id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String name;
    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", dataType = "String")
    private String pwd;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "用户昵称", dataType = "String")
    private String nickname;
    /**
     * 生日
     */
    @ApiModelProperty(value = "用户生日", dataType = "java.time.LocalDate")
    private LocalDate birthday;
    /**
     * 性别
     */
    @ApiModelProperty(value = "用户性别", dataType = "String")
    private String sex;
    /**
     * 个人介绍
     */
    @ApiModelProperty(value = "用户个人介绍", dataType = "String")
    private String introduction;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", dataType = "String")
    private String avatar;
    /**
     * 是否注销
     */
    @ApiModelProperty(value = "用户账户是否已经注销", dataType = "Boolean", example = "false")
    private Boolean logout;

    /**
     * 用户对应的问题列表
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "用户对应的问题列表", dataType = "List")
    private List<Question> questionList;

    /**
     * 用户对应的访问权限列表
     */
    @ApiModelProperty(value = "用户对应的访问权限列表", dataType = "Set")
    @TableField(exist = false)
    private Set<Permission> permissionSet;

    /**
     * 令牌
     */
    @ApiModelProperty(value = "用户令牌", dataType = "String")
    @TableField(exist = false)
    private String token;

    /**
     * 改变用户信息
     *
     * @param userInfo 用户信息
     */
    public void changeUserInfo (UserInfo userInfo) {
        nickname = userInfo.getNickname();
        birthday = userInfo.getBirthday();
        sex = userInfo.getSex();
        introduction = userInfo.getIntroduction();
        avatar = userInfo.getAvatar();
    }
}
