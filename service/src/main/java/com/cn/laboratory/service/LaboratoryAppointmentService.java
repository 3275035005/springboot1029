package com.cn.laboratory.service;

import com.cn.laboratory.entity.Laboratory;
import com.cn.laboratory.entity.LaboratoryAppointment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.laboratory.utils.page.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实验预约 服务类
 * </p>
 */
public interface LaboratoryAppointmentService extends IService<LaboratoryAppointment> {

    PageResult pageQuery(int page, int limit, LaboratoryAppointment data);

    PageResult pageQueryTeacher(int page, int limit, LaboratoryAppointment data);

    List<Map<String, Object>> getZzt(String thisMonth, int i);

    List<Map<String, Object>> getZb();



}
