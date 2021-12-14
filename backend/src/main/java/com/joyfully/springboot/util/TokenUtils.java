package com.joyfully.springboot.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.joyfully.springboot.entity.Admin;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.mapper.AdminMapper;
import com.joyfully.springboot.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Token工具类
 *
 * @author marx
 * @date 2021/11/11
 */
@Slf4j
@Component
public class TokenUtils {

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
     * 静态用户映射器
     */
    private static UserMapper staticUserMapper;
    /**
     * 静态管理员映射器
     */
    private static AdminMapper staticAdminMapper;

    /**
     * 初始化，springboot @Autowired @Resource 不支持静态变量
     * PostConstruct该注解被用来修饰一个非静态的void（）方法。被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。
     * PostConstruct在构造函数之后执行，init（）方法之前执行。
     * 通常我们会是在Spring框架中使用到@PostConstruct注解 该注解的方法在整个Bean初始化中的执行顺序：
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     */
    @PostConstruct
    public void init() {
        staticUserMapper = userMapper;
        staticAdminMapper = adminMapper;
    }

    /**
     * 生成token
     *
     * @param user 用户
     * @return {@link String}
     */
    public static String getToken(User user) {
        return JWT.create().withExpiresAt(DateUtil.offsetDay(new Date(), 1)).withAudience("user:" + user.getId().toString())
                .sign(Algorithm.HMAC256(user.getPwd()));
    }

    /**
     * 生成token
     *
     * @param admin 管理员
     * @return {@link String}
     */
    public static String getToken(Admin admin) {
        return JWT.create().withExpiresAt(DateUtil.offsetDay(new Date(), 1)).withAudience("admin:" + admin.getId().toString())
                .sign(Algorithm.HMAC256(admin.getPwd()));
    }

    /**
     * 获取token中的用户信息
     *
     * @return {@link User}
     */
    public static User getUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            String aud = JWT.decode(token).getAudience().get(0);
            String role = aud.split(":")[0];
            if (!"user".equals(role)) {
                return null;
            }
            Integer userId = Integer.valueOf(aud.split(":")[1]);
            return staticUserMapper.selectById(userId);
        } catch (Exception e) {
            log.error("解析token失败", e);
            return null;
        }
    }

    /**
     * 获取token中的用户信息
     *
     * @return {@link Admin}
     */
    public static Admin getAdmin() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            String aud = JWT.decode(token).getAudience().get(0);
            String role = aud.split(":")[0];
            if (!"admin".equals(role)) {
                return null;
            }
            Integer adminId = Integer.valueOf(aud.split(":")[1]);
            return staticAdminMapper.selectById(adminId);
        } catch (Exception e) {
            log.error("解析token失败", e);
            return null;
        }
    }
}
