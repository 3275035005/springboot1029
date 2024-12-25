package com.cn.laboratory.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实验试题分数信息表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LaboratoryInfoScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 试题标题
     */
    @TableField(exist = false)
    private String title;
    /**
     * 每个试题唯一值
     */
    private String infoId;

    /**
     * 答案内容
     */
    private String content;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 用户id
     */
    private Integer userId;


    /**
     * 实验预约id
     */
    private Integer appointmentId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
