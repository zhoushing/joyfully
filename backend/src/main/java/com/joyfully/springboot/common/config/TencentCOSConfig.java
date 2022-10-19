package com.joyfully.springboot.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯cosconfig
 *
 * @author marx
 * @date 2022/04/15
 */
@Data
@Component
@ConfigurationProperties(prefix = "tencent-cos")
public class TencentCOSConfig {
    /**
     * 存储桶访问url
     */
    private String baseUrl;
    /**
     * 应用程序id
     */
    private String appId;
    /**
     * 访问密钥
     */
    private String accessKey;
    /**
     * 秘密密钥
     */
    private String secretKey;
    /**
     * 地区名称
     */
    private String regionName;
    /**
     * 存储桶名称
     */
    private String bucketName;
    /**
     * 文件夹前缀
     */
    private String folderPrefix;
    /**
     * 最大文件大小
     */
    private String maxSize;
}
