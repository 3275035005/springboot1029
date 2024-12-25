package com.cn.laboratory.mapper;

import com.cn.laboratory.entity.LaboratoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 实验试题信息表 Mapper 接口
 * </p>
 */
public interface LaboratoryInfoMapper extends BaseMapper<LaboratoryInfo> {

    List<LaboratoryInfo> pageQuery(LaboratoryInfo data);

    int getCountGroupInfoId(@Param("laboratoryId") Integer laboratoryId,@Param("type") String type);

    List<LaboratoryInfo> getLaboratoryInfoById(Integer id);

}
