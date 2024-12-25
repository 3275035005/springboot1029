package com.cn.laboratory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.laboratory.entity.Notice;
import com.cn.laboratory.mapper.NoticeMapper;
import com.cn.laboratory.service.NoticeService;
import com.cn.laboratory.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 公告信息 服务实现类
 * </p>
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public PageResult pageQuery(int page, int limit, Notice data) {
        PageHelper.startPage(page, limit);
        List<Notice> queryList = baseMapper.pageQuery(data);
        PageInfo<Notice> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
