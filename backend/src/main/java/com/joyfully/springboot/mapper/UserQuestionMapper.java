package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyfully.springboot.entity.Question;
import com.joyfully.springboot.entity.UserQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author marx
 * @date 2022/01/09
 */
public interface UserQuestionMapper extends BaseMapper<UserQuestion> {
    /**
     * 按用户id删除
     * 将要删除的问题的关联用户关联为已注销
     *
     * @param userId 用户id
     * @return int
     */
    @Update("update user_question set user_id = 6 where user_id = #{userId}")
    public int deleteByUserId(Integer userId);

    /**
     * 查询问题通过用户id
     *
     * @param userId 用户id
     * @return {@link List}<{@link Question}>
     */
    @Select("select * from question where id in (select question_id from user_question where user_id = #{userId})")
    public List<Question> selectQuestionByUserId(Integer userId);

    /**
     * 查询问题id通过用户id
     *
     * @param userId 用户id
     * @return {@link List}<{@link Question}>
     */
    @Select("select question_id from user_question where user_id = #{userId}")
    public List<Integer> selectQuestionIdByUserId(Integer userId);

    /**
     * 查询重复
     *
     * @param userQuestion 用户问题
     * @return {@link List}<{@link Integer}>
     */
    @Select("select * from user_question where user_id = #{userId} and question_id = #{questionId}")
    public UserQuestion selectRepeat(UserQuestion userQuestion);
}
