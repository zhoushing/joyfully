package com.joyfully.springboot.util;

import cn.hutool.core.io.FileUtil;
import com.joyfully.springboot.common.config.TencentCOSConfig;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * cosclient工具
 *
 * @author marx
 * @date 2022/04/15
 */
public class COSClientUtil {
    /**
     * 日志记录器
     */
    private final Logger log = LoggerFactory.getLogger(COSClientUtil.class);
    /**
     * 获取配置信息
     */
    private static TencentCOSConfig cosConfig = SpringBootBeanUtil.getBean(TencentCOSConfig.class);
    /**
     * 初始化用户身份信息
     */
    private static COSCredentials cred = new BasicCOSCredentials(cosConfig.getAppId(), cosConfig.getAccessKey(), cosConfig.getSecretKey());
    /**
     * 设置bucket的区域
     */
    private static ClientConfig clientConfig = new ClientConfig(new Region(cosConfig.getRegionName()));
    /**
     * 生成COS客户端
     */
    private static COSClient cosClient = new COSClient(cred, clientConfig);



    /**
     * 上传文件
     *
     * @param file     文件
     * @param fileName 文件名称
     * @return {@link String}
     * @throws Exception 异常
     */
    public static String uploadFileToCos(MultipartFile file, String fileName) throws Exception {
        assert file != null : "The file is blank";

        int size = Integer.parseInt(cosConfig.getMaxSize());
        int maxSize = size << 20;
        if (file.getSize() > maxSize) {
            /* 抛出异常在此调整 */
            throw new Exception(HttpCodeEnum.FILE_SIZE_EXCEEDS_LIMIT.getMsg());
        }

        // 生成对象键
        String key = cosConfig.getFolderPrefix() + "/" + fileName;
        try {
            InputStream inputStream = file.getInputStream();
            uploadFileToCos(inputStream, key);
            // 文件下载路径
            return cosConfig.getBaseUrl() + "/" + key;
        } catch (Exception e) {
            /* 抛出异常在此调整 */
            System.out.println("异常类型" + e.getClass().getSimpleName());
            System.out.println("异常内容" + e.getMessage());
            e.printStackTrace();
            throw new Exception(HttpCodeEnum.FILE_UPLOAD_FAILED.getMsg());
        }
    }

    /**
     * 上传到COS服务器 如果同名文件会覆盖服务器上的
     *
     * @param inputStream 输入流
     * @param key         关键
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public static String uploadFileToCos(InputStream inputStream, String key) throws IOException {
        String etag = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置输入流长度为500
            objectMetadata.setContentLength(inputStream.available());
            // 设置 Content type
            objectMetadata.setContentType(getContentType(key.substring(key.lastIndexOf("."))));
            // 上传文件
            PutObjectResult putResult = cosClient.putObject(cosConfig.getBucketName(), key, inputStream, objectMetadata);
            etag = putResult.getETag();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (inputStream != null) {
                    // 关闭输入流
                    inputStream.close();
                }
                // 关闭客户端(关闭后台线程)
                cosClient.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return etag;
    }

    /**
     * Description: 判断Cos服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    private static String getContentType(String filenameExtension) {
        String bmp = "bmp";
        if (bmp.equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }
        String gif = "gif";
        if (gif.equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        String jpeg = "jpeg";
        String jpg = "jpg";
        String png = "png";
        if (jpeg.equalsIgnoreCase(filenameExtension) || jpg.equalsIgnoreCase(filenameExtension)
                || png.equalsIgnoreCase(filenameExtension)) {
            return "image/jpeg";
        }
        String html = "html";
        if (html.equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        String txt = "txt";
        if (txt.equalsIgnoreCase(filenameExtension)) {
            return "text/plain";
        }
        String vsd = "vsd";
        if (vsd.equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        String pptx = "pptx";
        String ppt = "ppt";
        if (pptx.equalsIgnoreCase(filenameExtension) || ppt.equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        String docx = ".docx";
        String doc = ".doc";
        if (docx.equalsIgnoreCase(filenameExtension) || doc.equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        String xml = "xml";
        if (xml.equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        String mp4 = ".mp4";
        if (mp4.equalsIgnoreCase(filenameExtension)) {
            return "application/octet-stream";
        }
        String pdf = ".pdf";
        if (pdf.equalsIgnoreCase(filenameExtension)) {
            // 使用流的形式进行上传，防止下载文件的时候访问url会预览而不是下载。  return "application/pdf";
            return "application/octet-stream";
        }
        String xls = ".xls";
        String xlsx = ".xlsx";
        if (xls.equalsIgnoreCase(filenameExtension) || xlsx.equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-excel";
        }
        String mp3 = ".mp3";
        if (mp3.equalsIgnoreCase(filenameExtension)) {
            return "audio/mp3";
        }
        String wav = ".wav";
        if (wav.equalsIgnoreCase(filenameExtension)) {
            return "audio/wav";
        }
        return "image/jpeg";
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @param response 响应
     * @throws IOException ioexception
     */
    public static void downloadFile(String fileName, HttpServletResponse response) throws IOException {
        // 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 要下载的文件路径和名称
        String key = cosConfig.getFolderPrefix() + "/" + fileName;

        // 指定要下载的文件所在的 bucket 和对象键
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosConfig.getBucketName(), key);

        COSObject cosObject = cosClient.getObject(getObjectRequest);

        COSObjectInputStream cosObjectInputStream = cosObject.getObjectContent();

        // 新建一个输出流对象
        OutputStream os;

        String showName = fileName.split("_", 2)[1];

        response.addHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(showName, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 通过文件路径读取文件字节流
        byte[] bytes = new byte[0];

        int read = cosObjectInputStream.read(bytes);

        // 通过输出流返回文件
        os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();

        cosClient.shutdown();
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String fileName) throws CosClientException, CosServiceException {
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // 要删除的路径
        String key = cosConfig.getFolderPrefix() + "/" + fileName;
        // 指定要删除的 bucket 和路径

        cosclient.deleteObject(cosConfig.getBucketName(), key);

        // 关闭客户端(关闭后台线程)
        cosclient.shutdown();
    }

}
