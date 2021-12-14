package com.joyfully.springboot.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * web日志切面
 *
 * @author marx
 * @date 2021/09/17
 */
@Aspect
@Component
public class WebLogAspect {
    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 控制器的日志
     * 目标类如下：
     * public * com.joyfully.springboot.controller..*.*(..))
     * 公开的 任意返回值的 controller层的 类以及子类的 所有方法
     */
    @Pointcut("execution(public * com.joyfully.springboot.controller..*.*(..))")
    public void controllerLog() {
    }

    @Before("controllerLog()") // 在切入点的方法运行之前要干的
    public void logBeforeController(JoinPoint joinPoint) {
        // 这个 RequestContextHolder 是 Springmvc 提供来获得请求的东西
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        // 断言其不为空
        assert requestAttributes != null;

        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

        // 记录下请求相关内容
        logger.info("################IP : " + request.getRemoteAddr());
        logger.info("################URL : " + request.getRequestURL().toString());
        logger.info("################HTTP_METHOD : " + request.getMethod());
        logger.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));

        // 下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        logger.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

    }
}
