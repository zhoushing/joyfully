package com.joyfully.springboot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 *
 * @author marx
 * @date 2022/01/13
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    /**
     * 超级管理员
     */
    SUPER_ADMIN(1, "超级管理员", "super admin"),

    /**
     * 普通用户
     */
    NORMAL_USER(2, "普通用户", "normal user"),

    /**
     * 审核员
     */
    AUDITOR(3, "审核员", "auditor");

    /**
     * 角色id
     */
    private final Integer roleId;

    /**
     * 名字
     */
    private final String name;

    /**
     * 描述
     */
    private final String description;
}
