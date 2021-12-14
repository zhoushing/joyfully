package com.joyfully.springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joyfully.springboot.entity.Admin;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.exception.CustomException;
import com.joyfully.springboot.mapper.AdminMapper;
import com.joyfully.springboot.mapper.UserMapper;
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
     * 管理员映射器
     */
    @Resource
    private AdminMapper adminMapper;

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
        if (StrUtil.isBlank(token)) {
            throw new CustomException("401", "未获取到token, 请重新登录");
        }
        DecodedJWT decode = JWT.decode(token);

        List<String> audience = decode.getAudience();

        judge(audience, token);

        return true;
    }

    /**
     * 判断 Token 是否合法
     *
     * @param audience 观众
     * @param token    令牌
     */
    public void judge(List<String> audience, String token) {
        String role = audience.get(0).split(":")[0];
        Integer id = Integer.valueOf(audience.get(0).split(":")[1]);
        String pwd = "";

        if ("user".equals(role)) {
            User user = userMapper.selectById(id);
            if (user == null) {
                throw new CustomException("401", "token不合法");
            }
            pwd = user.getPwd();
        }
        else if ("admin".equals(role)) {
            Admin admin = adminMapper.selectById(id);
            if (admin == null) {
                throw new CustomException("401", "token不合法");
            }
            pwd = admin.getPwd();
        }

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(pwd)).build();
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new CustomException("401", "token不合法");
        }
    }
}
