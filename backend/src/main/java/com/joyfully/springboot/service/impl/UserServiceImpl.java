package com.joyfully.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joyfully.springboot.common.Result;
import com.joyfully.springboot.controller.dto.FileOwnerInfo;
import com.joyfully.springboot.controller.dto.QuestionerInfo;
import com.joyfully.springboot.controller.dto.UserInfo;
import com.joyfully.springboot.entity.Permission;
import com.joyfully.springboot.entity.User;
import com.joyfully.springboot.entity.UserRole;
import com.joyfully.springboot.enums.HttpCodeEnum;
import com.joyfully.springboot.enums.RoleEnum;
import com.joyfully.springboot.exception.UserException;
import com.joyfully.springboot.mapper.*;
import com.joyfully.springboot.service.UserService;
import com.joyfully.springboot.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
 * 用户服务实现
 *
 * @author marx
 * @date 2021/07/31
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 用户映射器
     */
    @Resource
    UserMapper userMapper;

    /**
     * bcryct 加密编码器
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 用户问题映射器
     */
    @Resource
    UserQuestionMapper userQuestionMapper;

    /**
     * 用户角色映射器
     */
    @Resource
    UserRoleMapper userRoleMapper;

    /**
     * 角色权限映射器
     */
    @Resource
    RolePermissionMapper rolePermissionMapper;

    /**
     * 回答映射器
     */
    @Resource
    AnswerMapper answerMapper;

    /**
     * 登录
     *
     * @param user 用户
     * @return {@link UserInfo}
     */
    @Override
    public UserInfo login(User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName, user.getName()));

        // 未查出对应的用户名或者密码不匹配
        if (res == null || !bCryptPasswordEncoder.matches(user.getPwd(), res.getPwd())) {
            return null;
        }

        // 用户已注销或者已经被后台管理员删除
        if (res.getLogout()) {
            throw new UserException(HttpCodeEnum.USER_ACCOUNT_LOGOUT);
        }

        UserInfo userInfo = new UserInfo(res);

        // 获取当前用户对应的权限列表
        List<Integer> roleIdList = userRoleMapper.selectRoleIdByUserId(res.getId());
        List<Permission> permissionList = rolePermissionMapper.selectPermissionByRoleIdList(roleIdList);
        Set<Permission> permissionSet = new TreeSet<>(Comparator.comparingInt(Permission::getId));
        permissionSet.addAll(permissionList);

        // 填充权限集合
        userInfo.setPermissionSet(permissionSet);

        // 生成token
        String token = TokenUtil.getToken(res);
        userInfo.setToken(token);

        return userInfo;
    }

    /**
     * 添加指定角色的用户
     *
     * @param user     用户
     * @param roleEnum 用户角色
     * @return {@link User}
     */
    @Transactional
    public User addUser(User user, RoleEnum roleEnum) {
        // 设置默认昵称为 user+用户名
        if (user.getNickname() == null) {
            user.setNickname("user" + user.getName());
        }

        // 将密码转义
        user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));

        int insert = userMapper.insert(user);
        if (insert == 0) {
            throw new UserException(HttpCodeEnum.USER_REGISTER_ERROR);
        }

        UserRole userRole = new UserRole(user.getId(), roleEnum.getRoleId());
        userRoleMapper.insert(userRole);

        return user;
    }

    /**
     * 删除
     *
     * @param id id
     */
    @Override
    public void delete(Integer id) {
        User user = userMapper.selectById(id);

        if (user == null) {
            throw new UserException(HttpCodeEnum.USER_ACCOUNT_NOT_FIND);
        }
        else if (user.getLogout()) {
            throw new UserException(HttpCodeEnum.USER_ACCOUNT_LOGOUT);
        }

        /*// 真实删除
        int delete = userMapper.deleteById(id);*/

        /* 删除用户问题关系 */
        userQuestionMapper.deleteByUserId(id);

        /* 删除用户答案关系 */
        answerMapper.deleteAnswerByUserId(id);


        // 伪删除
        int delete = userMapper.delete(id);

        if (delete == 0) {
            throw new UserException(HttpCodeEnum.DB_DELETE_ERROR);
        }
    }

    /**
     * 更新
     *
     * @param user 用户
     */
    @Override
    public void update(User user) {
        int update = userMapper.updateById(user);

        if (update == 0) {
            throw new UserException(HttpCodeEnum.DB_UPDATE_ERROR);
        }
    }

    /**
     * 更新密码
     *
     * @param user     用户
     * @param password 密码
     * @param newPass  新通
     */
    @Override
    public void updatePassWord(User user, String password, String newPass) {
        if (!bCryptPasswordEncoder.matches(password, user.getPwd())) {
            throw new UserException(HttpCodeEnum.USER_PASSWORD_ERROR);
        }
        user.setPwd(bCryptPasswordEncoder.encode(newPass));
        userMapper.updateById(user);
    }

    /**
     * 得到用户信息通过id
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * 查询所有信息
     *
     * @param page         查询的页面
     * @param queryWrapper 查询包装
     * @return page
     */
    @Override
    public Page<User> findPage(Page page, Wrapper<User> queryWrapper) {
        return userMapper.selectPage(page, queryWrapper);
    }

    /**
     * 发现问
     *
     * @param limit 限制
     * @return {@link List}<{@link QuestionerInfo}>
     */
    @Override
    public List<QuestionerInfo> findQuestioner(Integer limit) {
        return userMapper.selectUserOrderByQuestionCount(limit);
    }

    /**
     * 找到回答者
     *
     * @param limit 限制
     * @return {@link List}<{@link QuestionerInfo}>
     */
    @Override
    public List<QuestionerInfo> findResponder(Integer limit) {
        return userMapper.selectUserOrderByAnswerCount(limit);
    }

    /**
     * 找到文件所有者
     *
     * @param limit 限制
     * @return {@link List}<{@link QuestionerInfo}>
     */
    @Override
    public List<FileOwnerInfo> findFileOwner(Integer limit) {
        return userMapper.selectUserOrderByFileCount(limit);
    }

    /**
     * Excel 导出用户信息
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    @Override
    public void export(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = CollUtil.newArrayList();
        List<User> all = userMapper.selectList(null);

        for (User user : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("用户名", user.getName());
            row1.put("昵称", user.getNickname());
            row1.put("出生日期", user.getBirthday());
            row1.put("性别", user.getSex());
            row1.put("个人描述", user.getIntroduction());
            row1.put("是否注销", user.getLogout() ? "是": "否");
            list.add(row1);
        }

        // 写 excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    /**
     * 上传
     *
     * @param file 文件
     * @throws IOException ioexception
     */
    @Override
    public void upload(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<User> saveList = new ArrayList<>();

        for (List<Object> row : lists) {
            User user = User.builder()
                    .name(row.get(0).toString())
                    .pwd("123456")
                    .nickname(row.get(1).toString())
                    .birthday(LocalDate.parse(row.get(2).toString().split(" ")[0],
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .sex(row.get(3).toString())
                    .introduction(row.get(4).toString())
                    .logout("是".equals(row.get(5)))
                    .build();

            // 将用户进行插入，MyBatisPlus操作完之后会将id填入
            user = addUser(user, RoleEnum.NORMAL_USER);
            saveList.add(user);
        }

        for (User user : saveList) {
            try {
                userMapper.insert(user);
            }
            catch (DataAccessException e) {
                throw new UserException("-1", "id为" + user.getId() + "的用户信息重复插入");
            }
            catch (Exception e) {
                throw new UserException("-1", "id为" + user.getId() + "的用户信息插入失败，请检查数据文件");
            }
        }
    }
}
