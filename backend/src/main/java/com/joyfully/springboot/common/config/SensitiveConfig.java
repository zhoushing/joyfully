package com.joyfully.springboot.common.config;

import com.joyfully.springboot.util.IllegalWordMatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 敏感配置
 *
 * @author marx
 * @date 2022/03/23
 */
@Configuration
public class SensitiveConfig {
    @Value("${sensitive.path}")
    private String path;

    @Value("${sensitive.replace}")
    private String replace;

    /**
     * 过滤器初始化
     *
     * @return {@link IllegalWordMatcher}
     * @throws IOException ioexception
     */
    @Bean
    public IllegalWordMatcher illegalWordMatcher() throws IOException {

        // 从目标路径文件读取对应的敏感词列表
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        IllegalWordMatcher matcher = new IllegalWordMatcher(replace);

        String str;

        while ((str = bufferedReader.readLine()) != null) {
            matcher.addWord(str);
        }

        return matcher;
    }
}
