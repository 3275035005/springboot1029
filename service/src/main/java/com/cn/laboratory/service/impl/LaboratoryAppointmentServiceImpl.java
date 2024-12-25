package com.cn.laboratory.service.impl;

import com.cn.laboratory.entity.LaboratoryAppointment;
import com.cn.laboratory.entity.User;
import com.cn.laboratory.mapper.LaboratoryAppointmentMapper;
import com.cn.laboratory.mapper.UserMapper;
import com.cn.laboratory.service.LaboratoryAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.laboratory.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实验预约 服务实现类
 * </p>
 */
@Service
public class LaboratoryAppointmentServiceImpl extends ServiceImpl<LaboratoryAppointmentMapper, LaboratoryAppointment> implements LaboratoryAppointmentService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult pageQuery(int page, int limit, LaboratoryAppointment data) {
        PageHelper.startPage(page, limit);
        List<LaboratoryAppointment> queryList = baseMapper.pageQuery(data);
        PageInfo<LaboratoryAppointment> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public PageResult pageQueryTeacher(int page, int limit, LaboratoryAppointment data) {
        PageHelper.startPage(page, limit);

        User user = userMapper.selectById(data.getTeacherId());

        if(user != null){
            if(!"1".equals(user.getRole())){
                data.setType(user.getType());
            }
        }
        List<LaboratoryAppointment> queryList = baseMapper.pageQueryTeacher(data);
        PageInfo<LaboratoryAppointment> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<Map<String, Object>> getZzt(String thisMonth, int size) {
        return baseMapper.getZzt(thisMonth,thisMonth.length(), size);
    }

    @Override
    public List<Map<String, Object>> getZb() {
        return baseMapper.getZb();
    }
}
