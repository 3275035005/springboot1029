package com.cn.laboratory.entity;

import lombok.Data;
import lombok.ToString;
import org.omg.CORBA.INTERNAL;

import java.util.List;

@Data
@ToString
public class SendScoreVo {


    /**
     * 预约id
     */
    private Integer appointmentId;

    /**
     * 评分教师id
     */
    private Integer teacherId;

    /**
     * 评价
     */
    private String evaluation;
    /**
     * 评分内容
     */
    private List<LaboratoryInfoScore> list;
}
