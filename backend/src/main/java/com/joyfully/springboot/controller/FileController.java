package com.joyfully.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.entity.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件控制器
 *
 * @author marx
 * @date 2021/08/03
 */
@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;

    @Value("${file.path}")
    private String path;

    /**
     * 上传接口
     *
     * @param file 文件
     * @return {@link Result <?>}
     * @throws IOException ioexception
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        // 获取源文件的名称
        String originalFilename = file.getOriginalFilename();
        // 定义文件的唯一标识（前缀）
        String uuid = IdUtil.fastSimpleUUID();
        // 获取上传的目标路径
        String rootFilePath = System.getProperty("user.dir") + path
                + uuid + "_" + originalFilename;
        // 将文件写入上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        // 返回结果
        return Result.success("http://" + ip + ":" + port + "/files/download/" + uuid);
    }

    /**
     * 下载接口
     *
     * @param response 响应
     * @param flag     标记
     * @return {@link Result<?>}
     */
    @GetMapping("/download/{flag}")
    public Result<?> getFiles(@PathVariable String flag, HttpServletResponse response) {
        // 新建一个输出流对象
        OutputStream os;
        // 文件存储的根路径
        String rootFilePath = System.getProperty("user.dir") + path;
        // 获取所有的文件名称
        List<String> fileNames = FileUtil.listFileNames(rootFilePath);

        // 找到和参数一致的文件
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");

        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                // 通过文件路径读取文件字节流
                byte[] bytes = FileUtil.readBytes(rootFilePath + fileName);
                // 通过输出流返回文件
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "文件下载失败");
        }

        return Result.success();
    }

    @GetMapping("/getFilesInfo")
    @ResponseBody
    public Result<?> getFilesInfo() {
        // 文件存储的根路径
        String rootFilePath = System.getProperty("user.dir") + path;
        // 获取所有的文件名称
        List<String> fileNames = FileUtil.listFileNames(rootFilePath);

        List<File> files = new ArrayList<>();

        for (int i = 0; i < fileNames.size(); i++) {
            String fileId = fileNames.get(i).split("_")[0];
            String fileName = fileNames.get(i).split("_")[1].split("\\.")[0];
            String fileType = fileNames.get(i).split("_")[1].split("\\.")[1];
            File file = new File(fileId, fileName, fileType);
            files.add(file);
        }

        return Result.success(files);
    }
}






































