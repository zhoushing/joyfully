package com.joyfully.springboot.service.impl;

import com.joyfully.springboot.service.LegalTestService;
import com.joyfully.springboot.util.IllegalWordMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 合法测试服务实现
 *
 * @author marx
 * @date 2022/03/23
 */
@Service
public class LegalTestServiceImpl implements LegalTestService {
    /**
     * 匹配器
     */
    @Resource
    IllegalWordMatcher matcher;

    /**
     * 检查
     *
     * @param text 文本
     * @return boolean true 合法 false 非法
     */
    @Override
    public boolean check(String text) {
        return matcher.isLegal(text);
    }

    /**
     * 取代
     *
     * @param text 文本
     * @return {@link String}
     */
    @Override
    public String replace(String text) {
        return matcher.replace(text);
    }

    /**
     * 取代
     *
     * @param text    文本
     * @param replace 取代
     * @return {@link String}
     */
    @Override
    public String replace(String text, String replace) {
        return matcher.replace(text, replace);
    }
}
