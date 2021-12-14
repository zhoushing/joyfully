package com.joyfully.springboot.common.config;

import com.joyfully.springboot.common.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 网络配置
 *
 * @author marx
 * @date 2021/11/11
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     *
     * @param registry 注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                // 匹配路径
                .addPathPatterns("/**")
                // 排除路径
                .excludePathPatterns("/user/login", "/user/register", "/admin/**", "/imserver/**", "/files/**", "/alipay/**",
                        "/doc.html", "/webjars/**", "/swagger-resources/**");

    }

    /**
     * 添加资源处理程序
     *
     * @param registry 注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置拦截器访问静态资源
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 身份验证拦截器
     *
     * @return {@link AuthInterceptor}
     */
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
}
