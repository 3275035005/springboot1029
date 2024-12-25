package com.cn.laboratory.service;

import com.cn.laboratory.entity.LaboratoryInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.laboratory.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 * 实验试题信息表 服务类
 * </p>
 */
public interface LaboratoryInfoService extends IService<LaboratoryInfo> {

    PageResult pageQuery(int page, int limit, LaboratoryInfo data);

    int getCountGroupInfoId(Integer laboratoryId, String type);

    List<LaboratoryInfo> getLaboratoryInfoById(Integer id);
}
