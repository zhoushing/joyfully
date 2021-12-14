package com.joyfully.springboot.common.config;

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

    /**
     * 最大生效时间 单位: 秒
     */
    private static final long MAX_AGE = 24 * 60 * 60;

    /**
     * 构建 Cors 跨域的相关属性配置
     *
     * @return {@link CorsConfiguration}
     */
    private CorsConfiguration buildConfig() {
        /*
         * 默认情况下，新创建的 CorsConfiguration 不允许任何跨域请求，必须明确配置以指示应该允许的内容。
         * 使用 applyPermitDefaultValues() 翻转初始化模型，以允许所有跨域请求的 GET、HEAD 和 POST 请求的开放默认值开始。
         */
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 设置访问源地址
        corsConfiguration.addAllowedOrigin("*");

        // 设置访问源请求头
        corsConfiguration.addAllowedHeader("*");

        // 设置访问源请求方法
        corsConfiguration.addAllowedMethod("*");

        // 设置最长生效时间
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

