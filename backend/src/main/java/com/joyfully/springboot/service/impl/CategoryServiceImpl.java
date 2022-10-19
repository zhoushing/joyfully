package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joyfully.springboot.entity.Category;
import com.joyfully.springboot.mapper.CategoryMapper;
import com.joyfully.springboot.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 类别服务实现
 *
 * @author marx
 * @date 2022/03/07
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    
}
