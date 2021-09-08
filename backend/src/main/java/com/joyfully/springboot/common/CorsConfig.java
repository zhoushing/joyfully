package com.joyfully.springboot.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 解决跨域配置
 * 允许了所有的外部形式的调用
 *
 * @author marx
 * @date 2021/08/03
 */
@Configuration
public class CorsConfig {

    private static final long MAX_AGE = 24 * 60 * 60;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 设置访问源地址
        corsConfiguration.addAllowedOrigin("*");
        // 设置访问源请求头
        corsConfiguration.addAllowedHeader("*");
        // 设置访问源请求方法
        corsConfiguration.addAllowedMethod("*");

        corsConfiguration.setMaxAge(MAX_AGE);
        return corsConfiguration;
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}

