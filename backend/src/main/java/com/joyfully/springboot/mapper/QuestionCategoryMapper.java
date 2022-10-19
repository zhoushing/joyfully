package com.joyfully.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joyfully.springboot.entity.Permission;
import com.joyfully.springboot.entity.QuestionCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 问题类别映射器
 *
 * @author marx
 * @date 2022/01/09
 */
public interface QuestionCategoryMapper extends BaseMapper<QuestionCategory> {
    /**
     * 删除关联通过问题id
     *
     * @param questionId 问题id
     * @return int
     */
    @Delete("delete from question_category where question_id = #{questionId}")
    public int deleteAssociationByQuestionId(Integer questionId);


    /**
     * 查询类别通过问题id
     *
     * @param questionId 问题id
     * @return {@link List}<{@link String}>
     */
    @Select("select name from category where id in (select category_id from question_category where question_id = #{questionId})")
    public List<String> selectCategoryByQuestionId(Integer questionId);
}
