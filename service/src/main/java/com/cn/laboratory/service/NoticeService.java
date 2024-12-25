package com.cn.laboratory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.laboratory.entity.Notice;
import com.cn.laboratory.utils.page.PageResult;

/**
 * <p>
 * 公告信息 服务类
 * </p>
 */
public interface NoticeService extends IService<Notice> {

    PageResult pageQuery(int page, int limit, Notice data);

}
