package com.joyfully.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.service.impl.QuestionServiceImpl;
import com.joyfully.springboot.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 用户控制器
 *
 * @author marx
 * @date 2021/07/31
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(description = "userService")
    UserServiceImpl userService;

    @Resource(description = "questionService")
    QuestionServiceImpl questionService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User res = userService.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName, user.getName())
                .eq(User::getPwd, user.getPwd()));
        if (res == null) {
            return Result.error("-1", "用户名不存在或者密码错误");
        }
        // 将查找出来res用户信息附带给data，用于前端的信息展示
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User selectOne = userService.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName, user.getName()));
        if (selectOne != null) {
            return Result.error("-1", "用户名已存在");
        }
        // 设置默认昵称为user加用户名
        if (user.getNickName() == null) {
            user.setNickName("user" + user.getName());
        }
        int res = userService.insert(user);
        if (res == 0) {
            return Result.error("-1", "注册失败");
        }
        return Result.success();
    }

    /**
     * 保存
     *
     * @param user 用户
     * @return {@link Result}
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody User user) {
        User selectOne = userService.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName, user.getName()));
        if (selectOne != null) {
            return Result.error("-1", "用户名重复");
        }

        // 没有用户名无法创建用户
        if (user.getName() == null) {
            return Result.error("-1", "用户名未填写！");
        }
        // 设置默认昵称为user加用户名
        if (user.getNickName() == null) {
            user.setNickName("user" + user.getName());
        }
        userService.insert(user);
        return Result.success();
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Result<?>}
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        // 一号特殊用户
        if (id == 1) {
            return Result.error("-1", "系统默认问题托管用户，不可删除！");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", id);
        questionService.deleteByMap(map);
        userService.deleteById(id);
        return Result.success();
    }

    /**
     * 更新
     *
     * @param user 用户
     * @return {@link Result<?>}
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody User user) {
        userService.updateById(user);
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
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        // search条件不为空时，执行模糊查询，否则查询所有
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getNickName, search);
        }
        Page<User> userPage = userService.findPage(new Page<>(pageNum, pageSize));
        return Result.success(userPage);

    }

    /**
     * Excel 导出
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = CollUtil.newArrayList();
        List<User> all = userService.selectList(null);
        for (User user : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("id", user.getId());
            row1.put("用户名", user.getName());
            row1.put("昵称", user.getNickName());
            row1.put("出生日期", user.getBirthday());
            row1.put("性别", user.getSex());
            row1.put("地址", user.getAddress());
            list.add(row1);
        }

        // 2. 写 excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformatsofficedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    /**
     * 导入的模板可以使用 Excel 导出的文件
     *
     * @param file Excel
     * @return {@link Result}<{@link ?}>
     * @throws IOException ioexception
     */
    @PostMapping("/import")
    public Result<?> upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<User> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            User user = new User();
            user.setName(row.get(1).toString());
            user.setNickName(row.get(2).toString());
            user.setBirthday(LocalDate.parse(row.get(3).toString().split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            user.setSex(row.get(5).toString());
            user.setAddress(row.get(5).toString());
            saveList.add(user);
        }
        for (User user : saveList) {
            try {
                userService.insert(user);
            }catch (Exception e) {
                return Result.error("-1", "insert error");
            }

        }
        return Result.success();
    }
}

































