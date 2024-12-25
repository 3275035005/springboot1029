package com.cn.laboratory.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 实验预约
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class LaboratoryAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 实验id
     */
    private Integer laboratoryId;

    /**
     * 预约时间
     */
    private String appointmentTime;

    /**
     * 实验状态 0未开始 1去实验 2实验结束 3未实验 4取消预约
     */
    private String status;

    /**
     * 实验提交时间
     */
    private Date laboratoryTime;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 评价
     */
    private String evaluation;

    /**
     * 教师评价id
     */
    private Integer teacherId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /**
     * 实验标题
     */
    @TableField(exist = false)
    private String title;


    /**
     * 实验科目 1化学 2生物 3物理
     */
    @TableField(exist = false)
    private String type;

    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 教师头像
     */
    @TableField(exist = false)
    private String teacherName;
}
