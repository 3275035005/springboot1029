package com.cn.laboratory.service.impl;

import com.cn.laboratory.entity.LaboratoryInfo;
import com.cn.laboratory.mapper.LaboratoryInfoMapper;
import com.cn.laboratory.service.LaboratoryInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.laboratory.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 实验试题信息表 服务实现类
 * </p>
 */
@Service
public class LaboratoryInfoServiceImpl extends ServiceImpl<LaboratoryInfoMapper, LaboratoryInfo> implements LaboratoryInfoService {

    @Override
    public PageResult pageQuery(int page, int limit, LaboratoryInfo data) {
        PageHelper.startPage(page, limit);
        List<LaboratoryInfo> queryList = baseMapper.pageQuery(data);
        PageInfo<LaboratoryInfo> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public int getCountGroupInfoId(Integer laboratoryId, String type) {
        return baseMapper.getCountGroupInfoId(laboratoryId, type);
    }

    @Override
    public List<LaboratoryInfo> getLaboratoryInfoById(Integer id) {
        return baseMapper.getLaboratoryInfoById(id);
    }
}
