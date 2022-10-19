package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyfully.springboot.controller.dto.FileOwnerInfo;
import com.joyfully.springboot.controller.dto.QuestionerInfo;
import com.joyfully.springboot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 用户映射器
 *
 * @author marx
 * @date 2021/07/31
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询所有用户名字
     *
     * @return {@link List}<{@link String}>
     */
    @Select("select name from user")
    List<String> selectAllUserName();

    /**
     * 伪删除
     *
     * @param id id
     * @return int
     */
    @Update("update user set logout = 1 where id = #{id}")
    int delete(Integer id);

    /**
     * 查询用户通过问题数
     *
     * @param limit 限制
     * @return {@link List}<{@link QuestionerInfo}>
     */
    List<QuestionerInfo> selectUserOrderByQuestionCount(Integer limit);

    /**
     * 查询用户通过回答数
     *
     * @param limit 限制
     * @return {@link List}<{@link QuestionerInfo}>
     */
    List<QuestionerInfo> selectUserOrderByAnswerCount(Integer limit);

    /**
     * 查询用户通过文件数
     *
     * @param limit 限制
     * @return {@link List}<{@link QuestionerInfo}>
     */
    List<FileOwnerInfo> selectUserOrderByFileCount(Integer limit);
}
