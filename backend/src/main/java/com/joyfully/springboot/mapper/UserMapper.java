package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.entity.Question;
import com.joyfully.springboot.entity.User;

/**
 * 用户映射器
 *
 * @author marx
 * @date 2021/07/31
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询所有信息
     * @param page 查询的页面
     * @return page
     */
    Page<User> findPage(Page<User> page);
}
