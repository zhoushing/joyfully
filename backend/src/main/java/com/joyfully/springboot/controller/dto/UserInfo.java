package com.joyfully.springboot.controller.dto;

import com.joyfully.springboot.entity.Permission;
import com.joyfully.springboot.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

/**
 * 用户信息
 *
 * @author marx
 * @date 2022/01/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserInfo", description = "用户信息")
public class UserInfo {
    /**
     * id
     */
    @ApiModelProperty(value = "用户ID", dataType = "Interger")
    private Integer id;
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
     * 令牌
     */
    @ApiModelProperty(value = "用户令牌", dataType = "String")
    private String token;
    /**
     * 用户对应的访问权限列表
     */
    @ApiModelProperty(value = "用户对应的访问权限列表", dataType = "Set")
    private Set<Permission> permissionSet;

    /**
     * 封装用户信息用于前端显示
     *
     * @param user 用户
     */
    public UserInfo(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.birthday = user.getBirthday();
        this.sex = user.getSex();
        this.introduction = user.getIntroduction();
        this.avatar = user.getAvatar();
        this.token = user.getToken();
        this.permissionSet = user.getPermissionSet();
    }
}
