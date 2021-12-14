package com.joyfully.springboot.controller;

import com.auth0.jwt.JWT;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 基本控制器
 * 继承本类的控制器都可以通过token来获取用户
 *
 * @author marx
 * @date 2021/11/11
 */
public class BaseController {
    /**
     * 用户映射器
     */
    @Resource
    private UserMapper userMapper;

    /**
     * 请求
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 通过token来获取用户
     *
     * @return {@link User}
     */
    public User getUser() {
        String token = request.getHeader("token");
        String audience = JWT.decode(token).getAudience().get(0);
        Integer userId = Integer.valueOf(audience);
        return userMapper.selectById(userId);
    }
}
