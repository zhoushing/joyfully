package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;


/**
 * 用户映射器
 *
 * @author marx
 * @date 2021/07/31
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询所有信息
     *
     * @param page         查询的页面
     * @param queryWrapper 查询包装
     * @return page
     */
    Page<User> findPage(Page page, @Param(Constants.WRAPPER) Wrapper<User> queryWrapper);
}
