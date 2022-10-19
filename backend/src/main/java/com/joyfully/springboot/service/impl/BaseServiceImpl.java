package com.joyfully.springboot.service.impl;

import com.auth0.jwt.JWT;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.mapper.UserMapper;
import com.joyfully.springboot.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 基础服务实现
 * 继承本类的Service都可以通过token或者HttpServletRequest来获取用户
 *
 * @author marx
 * @date 2022/01/09
 */
@Service("baseService")
public class BaseServiceImpl implements BaseService {
    /**
     * 用户映射器
     */
    @Resource
    private UserMapper userMapper;

    /**
     * 通过token来获取用户
     *
     * @param token 令牌
     * @return {@link User}
     */
    @Override
    public User getUser(String token) {
        String audience = JWT.decode(token).getAudience().get(0);
        Integer userId = Integer.valueOf(audience);
        return userMapper.selectById(userId);
    }

    /**
     * 获取用户
     *
     * @param request 请求
     * @return {@link User}
     */
    @Override
    public User getUser(HttpServletRequest request) {
        return getUser(request.getHeader("token"));
    }

    /**
     * 通过token来获取用户
     *
     * @param token 令牌
     * @return {@link User}
     */
    @Override
    public Integer getUserId(String token) {
        String audience = JWT.decode(token).getAudience().get(0);
        return Integer.valueOf(audience);
    }

    /**
     * 得到用户id
     *
     * @param request 请求
     * @return {@link Integer}
     */
    @Override
    public Integer getUserId(HttpServletRequest request) {
        return getUserId(request.getHeader("token"));
    }
}
