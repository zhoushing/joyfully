package com.joyfully.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 问题类别
 *
 * @author marx
 * @date 2021/12/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("question_category")
public class QuestionCategory {
    /**
     * 问题id
     */
    private Integer questionId;

    /**
     * 类别id
     */
    private Integer categoryId;
}
