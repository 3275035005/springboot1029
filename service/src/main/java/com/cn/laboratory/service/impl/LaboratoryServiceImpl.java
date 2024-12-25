package com.cn.laboratory.service.impl;

import com.cn.laboratory.entity.Laboratory;
import com.cn.laboratory.entity.User;
import com.cn.laboratory.mapper.LaboratoryMapper;
import com.cn.laboratory.service.LaboratoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.laboratory.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实验资源 服务实现类
 * </p>
 */
@Service
public class LaboratoryServiceImpl extends ServiceImpl<LaboratoryMapper, Laboratory> implements LaboratoryService {

    @Override
    public PageResult pageQuery(int page, int limit, Laboratory data) {
        PageHelper.startPage(page, limit);
        List<Laboratory> queryList = baseMapper.pageQuery(data);
        PageInfo<Laboratory> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<Map<String, Object>> getZb() {
        return baseMapper.getZb();
    }

}
