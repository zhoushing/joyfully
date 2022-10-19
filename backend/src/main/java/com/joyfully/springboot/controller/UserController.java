package com.joyfully.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.controller.dto.FileOwnerInfo;
import com.joyfully.springboot.controller.dto.QuestionerInfo;
import com.joyfully.springboot.controller.dto.UserInfo;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.enums.ExceptionEnum;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.enums.RoleEnum;
import com.joyfully.springboot.exception.UserException;
import com.joyfully.springboot.service.BaseService;
import com.joyfully.springboot.service.JedisService;
import com.joyfully.springboot.service.LegalTestService;
import com.joyfully.springboot.service.QuestionService;
import com.joyfully.springboot.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 用户控制器
 *
 * @author marx
 * @date 2021/07/31
 */
@Api(tags = "用户操作模块")
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 用户服务
     */
    @Resource(description = "userService")
    UserServiceImpl userService;

    /**
     * 问题服务
     */
    @Resource(description = "questionService")
    QuestionService questionService;

    /**
     * jedis服务
     */
    @Autowired
    JedisService jedisService;

    /**
     * 合法测试服务
     */
    @Resource
    LegalTestService legalTestService;

    /**
     * 基础服务
     */
    @Resource
    BaseService baseService;

    /**
     * 登录验证
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) throws UserException {
        if (!jedisService.selectUserName(user.getName())) {
            return Result.error(HttpCodeEnum.USER_ACCOUNT_NOT_FIND);
        }

        UserInfo login = userService.login(user);

        if (login == null) {
            return Result.error(HttpCodeEnum.USER_ACCOUNT_NOT_FIND);
        }

        // 将查找出来res用户信息附带给data，用于前端的信息展示
        return Result.success(login);
    }

    /**
     * 注册用户
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "user", value = "用户注册信息", dataType = "User", required = true)
    @ApiOperation("注册")
    @ApiOperationSupport(includeParameters = {"name", "pwd"})
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) throws UserException {
        if (!legalTestService.check(user.getName())) {
            return Result.error(HttpCodeEnum.USERNAME_CONTAINS_SENSITIVE_WORDS);
        }

        if (jedisService.selectUserName(user.getName())) {
            return Result.error(HttpCodeEnum.USERNAME_ALREADY_EXISTS);
        }

        User register;

        try {
            register = userService.addUser(user, RoleEnum.NORMAL_USER);
        } catch (Exception e) {
            return Result.error(HttpCodeEnum.USER_REGISTER_ERROR);
        }

        jedisService.insertUserName(register.getName());

        return Result.success();
    }

    /**
     * 保存
     * 前端暂未编写新增用户功能
     *
     * @param user 用户
     * @return {@link Result}
     */
    @ApiImplicitParam(name = "user", value = "新增用户信息", dataType = "User", required = true)
    @ApiOperation("新增用户")
    @PostMapping("/save")
    @Deprecated
    public Result<?> save(@RequestBody User user) throws UserException {
        if (jedisService.selectUserName(user.getName())) {
            return Result.error(HttpCodeEnum.USERNAME_ALREADY_EXISTS);
        }

        jedisService.insertUserName(user.getName());

        userService.save(user);

        return Result.success();
    }

    /**
     * 删除指定用户
     *
     * @param id 用户ID
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "user", value = "要被删除用户的信息", dataType = "User", required = true)
    @ApiOperation("删除用户")
    @ApiOperationSupport(includeParameters = "id")
    @DeleteMapping("/delete/{id}")
    @Transactional
    public Result<?> delete(@PathVariable Integer id) throws UserException {
        // 原来真的删除用户记录，但是现在只是伪删除，所以用户名缓存不删除
        /*// 清除用户名缓存
        jedisService.deleteUserName(user.getName());*/

        userService.delete(id);

        // 同时删除用户对应的问题（伪删除，仅删除用户问题关系）
        questionService.delete(id);
        return Result.success();
    }

    /**
     * 更新
     * 因为更新用户信息是不允许替换用户用户名的，所以此处不对缓存进行更替
     *
     * @param user 用户
     * @return {@link Result<?>}
     */
    @ApiImplicitParam(name = "user", value = "更新用户的信息", dataType = "User", required = true)
    @ApiOperation("更新用户")
    @ApiOperationSupport(includeParameters = {"nickname", "birthday", "sex", "introduction"})
    @PutMapping("/update")
    public Result<?> update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "user", value = "更新用户的信息", dataType = "User", required = true)
    @ApiOperation("更新用户信息")
    @ApiOperationSupport(includeParameters = {"avatar", "nickname", "birthday", "sex", "introduction"})
    @PutMapping("/update/userInfo")
    public Result<?> updateUserInfo(HttpServletRequest request, @RequestBody UserInfo userInfo) {
        User user = baseService.getUser(request.getHeader("token"));
        user.changeUserInfo(userInfo);

        userService.update(user);
        return Result.success();
    }

    /**
     * 改变密码
     *
     * @param request 请求
     * @param map     地图
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "map", value = "原密码和新密码", dataType = "Map", required = true)
    @ApiOperation("更新用户密码")
    @ApiOperationSupport(params = @DynamicParameters(properties = {
            @DynamicParameter(name = "password", value = "原密码"),
            @DynamicParameter(name = "newPass", value = "新密码")
    }))
    @PutMapping("/password")
    public Result<?> changePassWord(HttpServletRequest request, @RequestBody Map<String, String> map) {
        User user = baseService.getUser(request.getHeader("token"));
        if (user == null) {
            return Result.error(HttpCodeEnum.USER_ACCOUNT_NOT_FIND);
        }

        String password = map.get("password");
        String newPass = map.get("newPass");
        userService.updatePassWord(user, password, newPass);
        return Result.success();
    }

    /**
     * 得到用户信息通过token
     *
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("获得用户信息")
    @GetMapping
    public Result<?> getUser(HttpServletRequest request) {
        User user = baseService.getUser(request.getHeader("token"));
        return Result.success(new UserInfo(user));
    }

    /**
     * 得到用户信息通过昵称
     *
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("通过昵称获得用户信息")
    @GetMapping("/nickname")
    public Result<?> getUser(@RequestParam String nickname) {
        if (StrUtil.isBlank(nickname)) {
            return Result.error(HttpCodeEnum.THE_NICKNAME_EMPTY);
        }

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.like(User::getNickname, nickname);

        List<User> user = userService.list(wrapper);

        List<UserInfo> userInfoList = new ArrayList<>();

        user.forEach(temp -> userInfoList.add(new UserInfo(temp)));

        return Result.success(userInfoList);
    }

    /**
     * 查询用户列表
     *
     * @param pageNum  当前页面位置
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link Result<?>}
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "查询的页数", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "查询的页大小", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "search", value = "筛选信息", dataType = "String", defaultValue = "")
    })
    @ApiOperation("查询用户列表")
    @GetMapping("/find")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        // 要求账户未注销或者未被管理员删除
        wrapper.eq(User::getLogout, 0);

        // search条件不为空时，执行模糊查询，否则查询所有
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getNickname, search);
        }

        Page<User> userPage = userService.findPage(new Page<>(pageNum, pageSize), wrapper);

        return Result.success(userPage);
    }

    /**
     * 找到提问数最多的用户
     *
     * @param limit 限制
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "limit", value = "查找记录数", dataType = "Integer", defaultValue = "1")
    @ApiOperation("找到提问数最多的用户")
    @GetMapping("/questioner")
    public Result<?> findBestQuestioner(@RequestParam(defaultValue = "1") Integer limit) {
        List<QuestionerInfo> questionerInfoList = userService.findQuestioner(limit);

        return Result.success(questionerInfoList);
    }

    /**
     * 找到回答数最多的用户
     *
     * @param limit 限制
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "limit", value = "查找记录数", dataType = "Integer", defaultValue = "1")
    @ApiOperation("找到回答数最多的用户")
    @GetMapping("/responder")
    public Result<?> findBestResponder(@RequestParam(defaultValue = "1") Integer limit) {
        List<QuestionerInfo> questionerInfoList = userService.findResponder(limit);

        return Result.success(questionerInfoList);
    }

    /**
     * 找到文件数最多的用户
     *
     * @param limit 限制
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "limit", value = "查找记录数", dataType = "Integer", defaultValue = "1")
    @ApiOperation("找到文件数最多的用户")
    @GetMapping("/fileowner")
    public Result<?> findBestFileOwner(@RequestParam(defaultValue = "1") Integer limit) {
        List<FileOwnerInfo> fileOwnerInfoList = userService.findFileOwner(limit);

        return Result.success(fileOwnerInfoList);
    }

    /**
     * 导入
     *
     * @param file Excel
     * @return {@link Result}<{@link ?}>
     * @throws IOException ioexception
     */
    @ApiImplicitParam(name = "file", value = "要上传的用户信息", dataType = "org.springframework.web.multipart.MultipartFile", required = true)
    @ApiOperation("用户信息导入")
    @PostMapping("/import")
    public Result<?> upload(MultipartFile file) throws IOException {
        userService.upload(file);
        return Result.success();
    }

    /**
     * Excel 导出用户信息
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    @ApiOperation("用户信息导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        userService.export(response);
    }
}