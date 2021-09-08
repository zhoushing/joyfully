package com.joyfully.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.entity.Admin;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.service.impl.AdminServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户控制器
 *
 * @author marx
 * @date 2021/07/31
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource(description = "adminService")
    AdminServiceImpl adminService;

    @PostMapping("/changePwd")
    public Result<?> changePassword(@RequestBody Map<String, Object> datas) {
        Integer id = (Integer) datas.get("id");
        String sourcePwd = (String) datas.get("sourcePwd");
        String pwd = (String) datas.get("pwd");

        Admin admin = adminService.selectById(id);

        // 原密码输入错误
        if (!sourcePwd.equals(admin.getPwd())) {
            return Result.error("-1", "原密码错误");
        }

        admin.setPwd(pwd);
        adminService.updateById(admin);
        return Result.success();
    }

    @GetMapping("/checkPower")
    public Result<?> checkPower(@RequestParam Integer id) {
        return Result.success(adminService.checkPower(id));
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin admin) {
        Admin res = adminService.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getName, admin.getName())
                .eq(Admin::getPwd, admin.getPwd()));
        if (res == null) {
            return Result.error("-1", "用户名不存在或者密码错误");
        }
        // 将查找出来res用户信息附带给data，用于前端的信息展示
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody Admin admin) {
        Admin selectOne = adminService.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getName, admin.getName()));
        if (selectOne != null) {
            return Result.error("-1", "用户名已存在");
        }
        int res = adminService.insert(admin);
        if (res == 0) {
            return Result.error("-1", "注册失败");
        }
        return Result.success();
    }

    /**
     * 保存
     *
     * @param admin 用户
     * @return {@link Result}
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody Admin admin) {
        // 没有用户名无法创建用户
        if (admin.getName() == null) {
            return Result.error("-1", "用户名未填写！");
        }
        adminService.insert(admin);
        return Result.success();
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Result<?>}
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        adminService.deleteById(id);
        return Result.success();
    }

    /**
     * 更新
     *
     * @param admin 用户
     * @return {@link Result<?>}
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Result.success();
    }

    /**
     * 查询页面
     *
     * @param pageNum  当前页面位置
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link Result<?>}
     */
    @GetMapping("/find")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>lambdaQuery();
        // search条件不为空时，执行模糊查询，否则查询所有
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Admin::getName, search);
        }
        Page<Admin> adminPage = adminService.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(adminPage);
    }
}

































