package com.joyfully.springboot.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.entity.File;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.exception.FileException;
import com.joyfully.springboot.mapper.FileMapper;
import com.joyfully.springboot.service.FileService;
import com.joyfully.springboot.util.COSClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件服务实现
 *
 * @author marx
 * @date 2022/01/09
 */
@Service("fileService")
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Value("${file.ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    /**
     * 指定文件存取路径
     */
    @Value("${file.path}")
    private String path;

    /**
     * 文件映射器
     */
    @Resource
    FileMapper fileMapper;

    /**
     * 评价
     *
     * @param uuid         id
     * @param isBelittle 是赞美
     * @return boolean
     */
    @Override
    public boolean evaluate(String uuid, boolean isBelittle) {
        File file = fileMapper.selectById(uuid);
        if (file == null) {
            return false;
        }

        if (isBelittle) {
            file.setBelittleCount(file.getBelittleCount() + 1);
        } else {
            file.setPraiseCount(file.getPraiseCount() + 1);
        }

        fileMapper.updateById(file);

        return true;
    }

    /**
     * 得到通过id
     *
     * @param id id
     * @return {@link File}
     */
    @Override
    public File getById(String id) {
        return fileMapper.selectById(id);
    }

    /**
     * 上传接口(本地)
     *
     * @param file 文件
     * @return {@link Result <?>}
     * @throws IOException ioexception
     */
    @Deprecated
    public String uploadBefore(MultipartFile file, Integer userId) throws IOException, FileException {
        // 定义文件的唯一标识（前缀）
        String uuid = IdUtil.fastSimpleUUID();

        // 获取源文件的名称
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new FileException(HttpCodeEnum.FILE_UPLOAD_FAILED);
        }

        int begin = originalFilename.indexOf(".");
        int last = originalFilename.length();
        String filename = originalFilename.substring(0, begin);
        String fileType = originalFilename.substring(begin + 1, last);

        // 获取上传的目标路径
        String rootFilePath = System.getProperty("user.dir") + path
                + uuid + "_" + originalFilename;

        // 将文件写入上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);

        File file1 = new File(uuid, filename, fileType, userId);

        // 将文件信息录入数据库
        fileMapper.insert(file1);

        // 返回结果
        return "http://" + ip + ":" + port + "/file/download/" + uuid;
    }

    /**
     * 上传接口(腾讯云cos)
     *
     * @param file 文件
     * @return {@link Result <?>}
     */
    @Override
    public String upload(MultipartFile file, Integer userId) throws FileException {
        // 定义文件的唯一标识（前缀）
        String uuid = IdUtil.fastSimpleUUID();

        // 获取源文件的名称
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new FileException(HttpCodeEnum.UPLOADED_FILE_EXCEPTION);
        }

        int begin = originalFilename.indexOf(".");
        int last = originalFilename.length();
        String filename = originalFilename.substring(0, begin);
        String fileType = originalFilename.substring(begin + 1, last);

        // 定义文件的唯一标识（前缀）
        String fileName = uuid + "_" + file.getOriginalFilename();

        String fileUrl = "";

        try {
            fileUrl = COSClientUtil.uploadFileToCos(file, fileName);
        } catch (Exception e) {
            throw new FileException(HttpCodeEnum.FILE_UPLOAD_FAILED);
        }

        File file1 = new File(uuid, filename, fileType, userId);

        // 将文件信息录入数据库
        fileMapper.insert(file1);

        // 返回结果
        return fileUrl;
    }

    /**
     * 下载接口
     *
     * @param flag     标记
     * @param response 响应
     * @return {@link Result<?>}
     */
    @Override
    public void getFileByUUID(String flag, HttpServletResponse response) {
        File file = fileMapper.selectById(flag);

        if (file == null) {
            throw new FileException(HttpCodeEnum.FILE_NOT_EXIST);
        }

        String fileName = file.getUUID() + "_" + file.getName() + "." + file.getType();

        try {
            download(fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException(HttpCodeEnum.FILE_DOWNLOAD_FAILED);
        }
    }

    /**
     * 得到文件通过完整名字
     * 得到文件通过全名
     *
     * @param fullName     文件全称
     * @param response 响应
     * @return {@link String}
     */
    @Override
    public void getFileByFullName(String fullName, HttpServletResponse response) {
        try {
            download(fullName, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException(HttpCodeEnum.FILE_DOWNLOAD_FAILED);
        }
    }

    /**
     * 下载(本地)
     *
     * @param fullName 名字
     * @param response 响应
     */
    @Deprecated
    private void downloadBefore(String fullName, HttpServletResponse response) throws IOException {
        // 新建一个输出流对象
        OutputStream os;

        // 文件存储的根路径
        String rootFilePath = System.getProperty("user.dir") + path;

        response.addHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(fullName, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 通过文件路径读取文件字节流
        byte[] bytes = FileUtil.readBytes(rootFilePath + fullName);

        // 通过输出流返回文件
        os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }

    /**
     * 下载(腾讯云cos)
     *
     * @param fullName 名字
     * @param response 响应
     */
    private void download(String fullName, HttpServletResponse response) throws IOException {
        try {
            COSClientUtil.downloadFile(fullName, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件信息
     *
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @param search   搜索
     * @return {@link Page}<{@link File}>
     */
    @Override
    public Page<File> getFilesInfo(Integer pageNum, Integer pageSize, String search) {
        return getFilesInfo(pageNum, pageSize, search, null);
    }

    /**
     * 获取指定用户文件信息
     *
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @param search   搜索
     * @param userId   用户id
     * @return {@link Page}<{@link File}>
     */
    @Override
    public Page<File> getFilesInfo(Integer pageNum, Integer pageSize, String search, Integer userId) {
        LambdaQueryWrapper<File> wrapper = Wrappers.<File>lambdaQuery();
        // search条件不为空时，执行模糊查询，否则查询所有
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(File::getName, search);
        }
        if (userId != null) {
            wrapper.eq(File::getUserId, userId);
        }
        Page<File> filePage = fileMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        return filePage;
    }

    /**
     * 获取文件列表按点赞数排序
     *
     * @param limit 限制
     * @return {@link PageInfo}<{@link File}>
     */
    @Override
    public List<File> getFilesInfoOrderByPraiseCount(Integer limit) {
        return fileMapper.queryAllOrderByPraiseCount(limit);
    }
}
