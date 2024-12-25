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
 * 实验资源
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Laboratory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 实验标题
     */
    private String title;

    /**
     * 视频地址
     */
    private String videoPath;

    /**
     * 实验科目 1化学 2生物 3物理
     */
    private String type;

    /**
     * 实验状态(0关闭 1正常 )
     */
    private String status;

    /**
     * 预约人数
     */
    private Integer number;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 单选题个数
     */
    @TableField(exist = false)
    private Integer type1;

    /**
     * 简答题个数
     */
    @TableField(exist = false)
    private Integer type2;

    /**
     * 预约人数
     */
    @TableField(exist = false)
    private Integer appointmentNumber;

    /**
     * 已实验人数
     */
    @TableField(exist = false)
    private Integer yesNumber;

    /**
     * 未实验人数
     */
    @TableField(exist = false)
    private Integer noNumber;

    /**
     * 取消预约人数
     */
    @TableField(exist = false)
    private Integer closeNumber;
}
