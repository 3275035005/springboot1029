package com.cn.laboratory.service.impl;

import com.cn.laboratory.entity.User;
import com.cn.laboratory.mapper.UserMapper;
import com.cn.laboratory.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.laboratory.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public PageResult pageQuery(int page, int limit, User data) {
        PageHelper.startPage(page, limit);
        List<User> queryList = baseMapper.pageQuery(data);
        PageInfo<User> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
