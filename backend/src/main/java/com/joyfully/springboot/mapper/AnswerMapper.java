package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyfully.springboot.entity.Answer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;

/**
 * 回答映射器
 *
 * @author marx
 * @date 2021/11/03
 */
public interface AnswerMapper extends BaseMapper<Answer> {
    /**
     * 删除
     *
     * @param ans 答
     * @return int
     */
    @Delete("delete from answer where user_id = #{userId} and question_id = #{questionId}")
    public int delete(Answer ans);

    /**
     * 删除回答通过用户id
     *
     * @param userId 用户id
     * @return int
     */
    @Update("update answer set user_id = 6 where user_id = #{userId}")
    public int deleteAnswerByUserId(Integer userId);

    /**
     * 删除回答通过问题id
     *
     * @param questionId 问题id
     * @return int
     */
    @Delete("delete from answer where question_id = #{questionId}")
    public int deleteAnswerByQuestionId(Integer questionId);

    /**
     * 更新通过id
     *
     * @param answer 回答
     * @return int
     */
    @Override
    public int updateById(Answer answer);

    /**
     * 查询回答通过用户id
     *
     * @param userId 用户id
     * @return int
     */
    @Select("select * from answer where user_id = #{userId}")
    public Answer selectAnswerByUserId(Integer userId);

    /**
     * 查询回答通过问题id
     *
     * @param questionId 问题id
     * @return int
     */
    @Select("select * from answer where question_id = #{questionId}")
    public Answer selectAnswerByQuestionId(Integer questionId);

    /**
     * 查询回答通过id
     *
     * @param questionId 问题id
     * @param userId     用户id
     * @return int
     */
    @Select("select * from answer where user_id = #{userId} and question_id = #{questionId}")
    public Answer selectAnswerById(Serializable userId, Serializable questionId);
}
