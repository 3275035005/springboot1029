package com.cn.laboratory.mapper;

import com.cn.laboratory.entity.LaboratoryAppointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实验预约 Mapper 接口
 * </p>
 */
public interface LaboratoryAppointmentMapper extends BaseMapper<LaboratoryAppointment> {

    List<LaboratoryAppointment> pageQuery(LaboratoryAppointment data);

    List<LaboratoryAppointment> pageQueryTeacher(LaboratoryAppointment data);

    List<Map<String, Object>> getZzt(@Param("thisMonth")String thisMonth, @Param("stringSize") int stringSize, @Param("size") int size);

    List<Map<String, Object>> getZb();


}
