package com.joyfully.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.entity.File;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件服务
 *
 * @author marx
 * @date 2022/01/09
 */
public interface FileService extends IService<File>, EvaluationTargetService {
    /**
     * 上传
     * 上传接口
     *
     * @param file   文件
     * @param userId 用户id
     * @return {@link Result}<{@link ?}>
     * @throws IOException ioexception
     */
    public String upload(MultipartFile file, Integer userId) throws IOException;

    /**
     * 下载接口
     *
     * @param response 响应
     * @param flag     标记
     * @return {@link Result<?>}
     */
    public void getFileByUUID(String flag, HttpServletResponse response);

    /**
     * 得到文件通过完整名字
     * 得到文件通过全名
     *
     * @param response 响应
     * @param name     文件全程
     * @return {@link String}
     */
    public void getFileByFullName(String name, HttpServletResponse response);

    /**
     * 获取文件信息
     *
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link Page}<{@link File}>
     */
    public Page<File> getFilesInfo(Integer pageNum, Integer pageSize, String search);

    /**
     * 获取指定用户文件信息
     *
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @param search   搜索
     * @param userId   用户id
     * @return {@link Page}<{@link File}>
     */
    public Page<File> getFilesInfo(Integer pageNum, Integer pageSize, String search, Integer userId);

    /**
     * 获取文件列表按点赞数排序
     *
     * @param limit 限制
     * @return {@link PageInfo}<{@link File}>
     */
    public List<File> getFilesInfoOrderByPraiseCount(Integer limit);
}
