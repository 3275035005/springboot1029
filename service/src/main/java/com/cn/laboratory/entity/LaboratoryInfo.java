package com.cn.laboratory.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实验试题信息表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LaboratoryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 每个试题唯一值
     */
    private String infoId;

    /**
     * 实验id
     */
    private Integer laboratoryId;

    /**
     * 试题类型(1选择题 2简答题)
     */
    private String type;

    /**
     * 试题内容
     */
    private String title;

    /**
     * 答案序号(A、B、C、D...)
     */
    private String sort;

    /**
     * 答案内容
     */
    private String content;

    /**
     * 是否为答案(0否 1是)
     */
    private Boolean flag;

    /**
     * 选择题类型(1仪器 2试剂)
     */
    private String tag;

    /**
     * 创建题库时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /**
     * 实验资源名称
     */
    @TableField(exist = false)
    private String laboratoryName;


    /**
     * 存放明细
     */
    @TableField(exist = false)
    private List<LaboratoryInfo> list;
}
