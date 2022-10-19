package com.joyfully.springboot.service;

import com.joyfully.springboot.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础服务
 *
 * @author marx
 * @date 2022/01/09
 */
public interface BaseService {
    /**
     * 通过token来获取用户
     *
     * @param token 令牌
     * @return {@link User}
     */
    User getUser(String token);

    /**
     * 获取用户
     *
     * @param request 请求
     * @return {@link User}
     */
    User getUser(HttpServletRequest request);

    /**
     * 通过token来获取用户ID
     *
     * @param token 令牌
     * @return {@link User}
     */
    Integer getUserId(String token);

    /**
     * 得到用户id
     *
     * @param request 请求
     * @return {@link Integer}
     */
    Integer getUserId(HttpServletRequest request);
}
