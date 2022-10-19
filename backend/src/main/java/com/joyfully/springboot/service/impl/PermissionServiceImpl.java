package com.joyfully.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joyfully.springboot.entity.Permission;
import com.joyfully.springboot.mapper.PermissionMapper;
import com.joyfully.springboot.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * 许可服务实现
 *
 * @author marx
 * @date 2022/03/07
 */
@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
