package com.cn.laboratory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.laboratory.entity.Notice;

import java.util.List;

/**
 * <p>
 * 公告信息 Mapper 接口
 * </p>
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    List<Notice> pageQuery(Notice data);

}