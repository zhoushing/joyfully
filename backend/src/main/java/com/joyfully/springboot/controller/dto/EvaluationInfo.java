package com.joyfully.springboot.controller.dto;

import com.joyfully.springboot.enums.EvaluationEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 举报信息
 *
 * @author marx
 * @date 2022/03/20
 */
@Data
@NoArgsConstructor
@Builder
@ApiModel(value = "EvaluationInfo", description = "评价信息")
public class EvaluationInfo {
    private Integer id;

    /**
     * 目标
     */
    @ApiModelProperty(value = "评价目标", dataType = "String")
    private String target;

    /**
     * 目标id，由target一起显示，方便数据库查询，留给前端拆分
     */
    /*@ApiModelProperty(value = "评价目标ID", dataType = "Integer")
    private Integer targetId;*/


    /**
     * 评价类型
     */
    @ApiModelProperty(value = "评价类型", dataType = "String")
    private String type;

    public void setType(String type) {
        if ("2".equals(type)) {
            this.type = EvaluationEnum.REPORT;
        }
        else if ("1".equals(type)) {
            this.type = EvaluationEnum.BELITTLE;
        }
        else if ("0".equals(type)) {
            this.type = EvaluationEnum.PRAISE;
        }
        else {
            this.type = type;
        }
    }

    /**
     * 原因
     */
    @ApiModelProperty(value = "举报原因", dataType = "String")
    private String reason;

    /**
     * 评价人昵称
     */
    @ApiModelProperty(value = "评价人ID", dataType = "Integer")
    private Integer userId;

    /**
     * 评价人昵称
     */
    @ApiModelProperty(value = "评价人昵称", dataType = "String")
    private String nickname;

    public EvaluationInfo(Integer id, String target, String type, String reason, Integer userId, String nickname) {
        this.id = id;
        this.target = target;
        setType(type);
        this.reason = reason;
        this.userId = userId;
        this.nickname = nickname;
    }
}
