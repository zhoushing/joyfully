package com.joyfully.springboot.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.controller.dto.FileOwnerInfo;
import com.joyfully.springboot.controller.dto.QuestionerInfo;
import com.joyfully.springboot.controller.dto.UserInfo;
import com.joyfully.springboot.entity.Question;
import com.joyfully.springboot.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户服务
 *
 * @author marx
 * @date 2021/07/31
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     *
     * @param user 用户
     * @return {@link User}
     */
    public UserInfo login(User user);

    /**
     * 删除
     *
     * @param id id
     */
    public void delete(Integer id);

    /**
     * 更新
     *
     * @param user 用户
     */
    public void update(User user);

    /**
     * 更新密码
     *
     * @param user     用户
     * @param password 密码
     * @param newPass  新通
     */
    public void updatePassWord(User user, String password, String newPass);

    /**
     * 得到用户通过id
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    public User getUserById(Integer id);

    /**
     * 下载
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    public void export(HttpServletResponse response) throws IOException;

    /**
     * 上传
     *
     * @param file 文件
     * @throws IOException ioexception
     */
    public void upload(MultipartFile file) throws IOException;

    /**
     * 查询所有信息
     *
     * @param page         查询的页面
     * @param queryWrapper 查询包装
     * @return page
     */
    Page<User> findPage(Page page, Wrapper<User> queryWrapper);

    /**
     * 发现问
     *
     * @param limit 限制
     * @return {@link List}<{@link QuestionerInfo}>
     */
    List<QuestionerInfo> findQuestioner(Integer limit);

    /**
     * 找到回答者
     *
     * @param limit 限制
     * @return {@link List}<{@link QuestionerInfo}>
     */
    List<QuestionerInfo> findResponder(Integer limit);

    /**
     * 找到文件所有者
     *
     * @param limit 限制
     * @return {@link List}<{@link QuestionerInfo}>
     */
    List<FileOwnerInfo> findFileOwner(Integer limit);
}
