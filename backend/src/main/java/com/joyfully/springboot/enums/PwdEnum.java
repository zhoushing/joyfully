package com.joyfully.springboot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * pwd枚举
 *
 * @author marx
 * @date 2022/01/13
 */
@Getter
@AllArgsConstructor
public enum PwdEnum {
    /**
     * 默认密码
     */
    PASSWORD("123456");

    /**
     * 密码
     */
    private final String password;
}
