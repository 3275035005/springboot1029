package com.cn.laboratory.service;

import com.cn.laboratory.entity.LaboratoryInfoScore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 实验试题分数信息表 服务类
 * </p>
 */
public interface LaboratoryInfoScoreService extends IService<LaboratoryInfoScore> {

    List<LaboratoryInfoScore> getLaboratoryInfoByAppointmentId(Integer id);
}
