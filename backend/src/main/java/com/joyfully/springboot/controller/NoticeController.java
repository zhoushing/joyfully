package com.joyfully.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.entity.Notice;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.exception.NoticeException;
import com.joyfully.springboot.service.BaseService;
import com.joyfully.springboot.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公告控制器
 *
 * @author marx
 * @date 2022/04/22
 */
@Api(tags = "公告操作模块")
@RestController
@RequestMapping("/notice")
public class NoticeController {
    /**
     * 通知服务
     */
    @Resource
    NoticeService noticeService;

    /**
     * 基础服务
     */
    @Resource
    BaseService baseService;

    /**
     * 保存公告
     *
     * @param notice 公告
     * @return {@link Result}<{@link ?}>
     * @throws NoticeException 公告异常
     */
    @ApiImplicitParam(name = "notice", value = "要添加的公告", dataType = "Notice", required = true)
    @ApiOperation("添加公告")
    @PostMapping("/save")
    public Result<?> save(@RequestBody Notice notice, HttpServletRequest request) throws NoticeException {
        String token = request.getHeader("token");

        User user = baseService.getUser(token);

        notice.setPublisher(user.getNickname());

        noticeService.save(notice);
        return Result.success();
    }

    /**
     * 删除指定公告
     *
     * @param id id
     * @return {@link Result<?>}
     */
    @ApiImplicitParam(name = "id", value = "要删除的公告ID", dataType = "Integer", required = true)
    @ApiOperation("删除指定公告")
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id) throws NoticeException {
        noticeService.removeById(id);
        return Result.success();
    }

    /**
     * 更新公告
     *
     * @param notice 公告
     * @return {@link Result<?>}
     */
    @ApiImplicitParam(name = "notice", value = "要更新的公告", dataType = "Notice", required = true)
    @ApiOperation("更新公告")
    @PutMapping("/update")
    public Result<?> update(@RequestBody Notice notice) throws NoticeException {
        noticeService.updateById(notice);
        return Result.success();
    }

    /**
     * 置顶或者取消置顶公告
     *
     * @param notice 公告
     * @return {@link Result<?>}
     */
    @ApiImplicitParam(name = "notice", value = "要置顶的公告", dataType = "Notice", required = true)
    @ApiOperation("调整公告优先级")
    @PutMapping("/top")
    public Result<?> updateTop(@RequestBody Notice notice) throws NoticeException {
        if (notice.getPriority() == 1) {
            notice.setPriority(5);
        }
        else {
            notice.setPriority(1);
        }

        noticeService.updateById(notice);
        return Result.success(notice.getPriority());
    }



    /**
     * 查询公告列表
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
    @ApiOperation("查询公告列表")
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Notice> wrapper = Wrappers.lambdaQuery();

        if (StrUtil.isNotBlank(search)) {
           wrapper.like(Notice::getTitle, search);
        }

        Page<Notice> page = noticeService.page(new Page<>(pageNum, pageSize), wrapper);

        return Result.success(page);
    }

    /**
     * 查询优先级高且最新发布的公告列表
     *
     * @param limit 限制
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "limit", value = "查询的页数", dataType = "String", defaultValue = "3")
    @ApiOperation("查询最近的公告列表")
    @GetMapping("/recent")
    public Result<?> findRecentNotice(@RequestParam(defaultValue = "3") Integer limit) {
        LambdaQueryWrapper<Notice> wrapper = Wrappers.lambdaQuery();

        wrapper.orderByDesc(Notice::getPriority).orderByDesc(Notice::getDate);

        List<Notice> page = noticeService.list(wrapper)
                                            .stream()
                                            .limit(limit)
                                            .collect(Collectors.toList());

        return Result.success(page);
    }
}
