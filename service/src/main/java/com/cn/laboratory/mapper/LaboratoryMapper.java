package com.cn.laboratory.mapper;

import com.cn.laboratory.entity.Laboratory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实验资源 Mapper 接口
 * </p>
 */
public interface LaboratoryMapper extends BaseMapper<Laboratory> {

    List<Laboratory> pageQuery(Laboratory data);


    List<Map<String, Object>> getZb();


}
