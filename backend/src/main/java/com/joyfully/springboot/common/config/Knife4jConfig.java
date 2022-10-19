package com.joyfully.springboot.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Swagger配置
 *
 * @author marx
 * @date 2022/03/12
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {
    /**
     * 云api
     * 地址 ip:port/doc
     * @return {@link Docket}
     */
    @Bean(value = "JoyFully-Api")
    public Docket cloudApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("接口文档列表")
                        .description("接口文档")
                        .termsOfServiceUrl("http://www.joyfully.com")
                        .contact(new Contact("徐年", "blog.xuyicheng.top", "zh.shing@foxmail.com"))
                        .version("1.0")
                        .build())
                // 分组名称
                .groupName("2.X版本")
                .select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.joyfully.springboot.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
