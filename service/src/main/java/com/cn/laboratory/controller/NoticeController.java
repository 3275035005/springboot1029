package com.cn.laboratory.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.laboratory.entity.Notice;
import com.cn.laboratory.service.NoticeService;
import com.cn.laboratory.utils.page.PageResult;
import com.cn.laboratory.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 公告信息 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {


    @Autowired
    private NoticeService service;

    /**
     * 分页条件查询
     * @param page   当前页码
     * @param limit 每页的大小
     * @param data 封装查询条件数据
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public R getPageData(
            @PathVariable int page,
            @PathVariable int limit,
            @RequestBody Notice data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }

    /**
     * 修改操作
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody Notice data){
        service.updateById(data);
        return R.ok();
    }

    /**
     * 新增操作
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody Notice data){
        service.save(data);
        return R.ok();
    }

    /**
     * 删除操作
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R delete(@PathVariable Integer id){
        service.removeById(id);
        return R.ok();
    }

    /**
     * 查询所有公告
     * @return
     */
    @GetMapping("getNoticeAll")
    public R getNoticeAll(){
        List<Notice> notice = service.list(new QueryWrapper<Notice>().orderByDesc("create_time"));
        return R.ok().data("list", notice);
    }
}

