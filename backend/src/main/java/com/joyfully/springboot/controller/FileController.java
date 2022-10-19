package com.joyfully.springboot.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.entity.File;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.exception.CustomException;
import com.joyfully.springboot.service.BaseService;
import com.joyfully.springboot.service.FileService;
import com.joyfully.springboot.util.COSClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件控制器
 *
 * @author marx
 * @date 2021/08/03
 */
@Api(tags = "文件操作模块")
@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 文件服务
     */
    @Resource(name = "fileService")
    FileService fileService;

    /**
     * 基础服务
     */
    @Resource
    BaseService baseService;

    /**
     * 上传接口
     *
     * @param file 文件
     * @return {@link Result <?>}
     * @throws IOException ioexception
     */
    @ApiImplicitParam(name = "file", value = "要上传的文件", dataType = "org.springframework.web.multipart.MultipartFile", required = true)
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println("file.getName() = " + file.getName());

        String token = request.getHeader("token");

        Integer userId = baseService.getUserId(token);
        String upload = fileService.upload(file, userId);

        return Result.success(upload);
    }

    /**
     * 删除
     *
     * @param uuid uuid
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "uuid", value = "要删除文件的uuid", dataType = "String", required = true)
    @ApiOperation("文件删除")
    @DeleteMapping("/{uuid}")
    @Transactional
    public Result<?> delete(@PathVariable String uuid) {
        // 删除在数据库中的数据，实际文件不删除
        fileService.removeById(uuid);
        return Result.success();
    }

    /**
     * 下载接口
     *
     * @param response 响应
     * @param flag     标记
     * @return {@link Result<?>}
     */
    @ApiImplicitParam(name = "flag", value = "要下载的文件标志", dataType = "String", required = true)
    @ApiOperation("文件下载通过限定UUID")
    @GetMapping("/download/{flag}")
    public Result<?> getFileByUUID(@PathVariable String flag, HttpServletResponse response) throws CustomException {
        fileService.getFileByUUID(flag, response);
        return Result.success();
    }

    /**
     * 得到文件通过完整名字
     *
     * @param response 响应
     * @param fullName 文件全名
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParam(name = "fullName", value = "要下载的文件名", dataType = "String", required = true)
    @ApiOperation("文件下载通过全名")
    @GetMapping("/downloadByFullName/{fullName}")
    public Result<?> getFileByFullName(@PathVariable String fullName, HttpServletResponse response) throws CustomException {
        fileService.getFileByFullName(fullName, response);
        return Result.success();
    }

    /**
     * 获取文件信息
     *
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "查询的页数", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "查询的页大小", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "search", value = "筛选信息", dataType = "String", defaultValue = "")
    })
    @ApiOperation("获取文件信息")
    @GetMapping("/getFilesInfo")
    @ResponseBody
    public Result<?> getFilesInfo(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "") String search) {
        Page<File> filesInfo = fileService.getFilesInfo(pageNum, pageSize, search);
        return Result.success(filesInfo);
    }

    /**
     * 获取文件信息
     *
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "查询的页数", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "查询的页大小", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "search", value = "筛选信息", dataType = "String", defaultValue = "")
    })
    @ApiOperation("获取文件信息")
    @GetMapping("/getFilesInfo/user")
    @ResponseBody
    public Result<?> getFilesInfoByUser(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "") String search,
                                        HttpServletRequest request) {
        String token = request.getHeader("token");
        Integer userId = baseService.getUserId(token);
        Page<File> filesInfo = fileService.getFilesInfo(pageNum, pageSize, search, userId);
        return Result.success(filesInfo);
    }

    /**
     * 获取文件列表按点赞数
     *
     * @param limit 限制
     * @return {@link Result}<{@link ?}>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "查询的文件数", dataType = "Integer", defaultValue = "5")
    })
    @ApiOperation("获取文件列表按点赞数")
    @GetMapping("/findByPraise")
    @ResponseBody
    public Result<?> getFilesOrderByPraiseCount(@RequestParam Integer limit) {
        List<File> filesInfo = fileService.getFilesInfoOrderByPraiseCount(limit);
        return Result.success(filesInfo);
    }


}