package com.cn.laboratory.service.impl;

import com.cn.laboratory.entity.LaboratoryInfoScore;
import com.cn.laboratory.mapper.LaboratoryInfoScoreMapper;
import com.cn.laboratory.service.LaboratoryInfoScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 实验试题分数信息表 服务实现类
 * </p>
 */
@Service
public class LaboratoryInfoScoreServiceImpl extends ServiceImpl<LaboratoryInfoScoreMapper, LaboratoryInfoScore> implements LaboratoryInfoScoreService {

    @Override
    public List<LaboratoryInfoScore> getLaboratoryInfoByAppointmentId(Integer id) {
        return baseMapper.getLaboratoryInfoByAppointmentId(id);
    }
}
