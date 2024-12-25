package com.cn.laboratory.service;

import com.cn.laboratory.entity.Laboratory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.laboratory.utils.page.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实验资源 服务类
 * </p>
 */
public interface LaboratoryService extends IService<Laboratory> {

    PageResult  pageQuery(int page, int limit, Laboratory data);

    List<Map<String, Object>> getZb();



}
