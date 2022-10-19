package com.joyfully.springboot.service;

/**
 * 合法测试服务
 *
 * @author marx
 * @date 2022/03/23
 */
public interface LegalTestService {
    /**
     * 检查
     *
     * @param text 文本
     * @return boolean  true 合法 false 非法
     */
    boolean check(String text);

    /**
     * 取代
     *
     * @param text 文本
     * @return {@link String}
     */
    String replace(String text);

    /**
     * 取代
     *
     * @param text    文本
     * @param replace 取代
     * @return {@link String}
     */
    String replace(String text, String replace);
}
