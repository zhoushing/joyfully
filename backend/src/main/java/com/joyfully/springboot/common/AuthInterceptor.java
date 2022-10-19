package com.joyfully.springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.exception.CustomException;
import com.joyfully.springboot.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 身份验证拦截器
 *
 * @author marx
 * @date 2021/11/11
 */
public class AuthInterceptor implements HandlerInterceptor {

    /**
     * 用户映射器
     */
    @Resource
    private UserMapper userMapper;

    /**
     * 预先处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");

        System.out.println("source-url: " + request.getRequestURI());

        if (StrUtil.isBlank(token)) {
            throw new CustomException("401", "未获取到token, 请重新登录");
        }

        Integer userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));

        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new CustomException("401", "token不合法");
        }

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPwd())).build();
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new CustomException("401", "token不合法");
        }
        return true;
    }
}
