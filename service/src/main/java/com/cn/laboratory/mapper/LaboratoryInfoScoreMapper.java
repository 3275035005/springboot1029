package com.cn.laboratory.mapper;

import com.cn.laboratory.entity.LaboratoryInfoScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 实验试题分数信息表 Mapper 接口
 * </p>
 */
public interface LaboratoryInfoScoreMapper extends BaseMapper<LaboratoryInfoScore> {

    List<LaboratoryInfoScore> getLaboratoryInfoByAppointmentId(Integer id);

}
