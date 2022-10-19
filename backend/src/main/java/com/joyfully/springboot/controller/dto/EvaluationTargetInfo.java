package com.joyfully.springboot.controller.dto;

import com.joyfully.springboot.entity.Answer;
import com.joyfully.springboot.entity.File;
import com.joyfully.springboot.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评价目标信息
 *
 * @author marx
 * @date 2022/04/03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationTargetInfo<T> {
    private T obj;
    private String target;

    public static EvaluationTargetInfo getTargetInfo(Object obj) {
        if (obj instanceof Answer) {
            return new EvaluationTargetInfo<>((Answer) obj, "answer");
        }
        else if (obj instanceof Question) {
            return new EvaluationTargetInfo<>((Question) obj, "question");
        }
        else if (obj instanceof File) {
            return new EvaluationTargetInfo<>((File) obj, "file");
        }

        return null;
    }
}
