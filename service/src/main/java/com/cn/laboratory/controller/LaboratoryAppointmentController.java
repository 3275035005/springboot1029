package com.cn.laboratory.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cn.laboratory.entity.Laboratory;
import com.cn.laboratory.entity.LaboratoryAppointment;
import com.cn.laboratory.entity.User;
import com.cn.laboratory.service.LaboratoryAppointmentService;
import com.cn.laboratory.service.LaboratoryService;
import com.cn.laboratory.service.UserService;
import com.cn.laboratory.utils.page.PageResult;
import com.cn.laboratory.utils.response.R;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 实验预约 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/laboratory-appointment")
public class LaboratoryAppointmentController {

    @Autowired
    private LaboratoryAppointmentService service;

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
            @RequestBody LaboratoryAppointment data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }


    /**
     * 实验预约
     * @param data
     * @return
     */
    @PostMapping("appointment")
    public R appointment(@RequestBody LaboratoryAppointment data){
        LaboratoryAppointment laboratoryAppointment = service.getOne(new QueryWrapper<LaboratoryAppointment>().eq("user_id",
                data.getUserId()).eq("laboratory_id", data.getLaboratoryId()).eq("status", "0"));
        if(laboratoryAppointment != null){
            return R.error("您已经预约过实验了");
        }
        data.setStatus("0");
        service.save(data);

        return R.ok();
    }

    /**
     * 根据教师用户id查询自己科目的预约记录
     */
    @PostMapping("pageQueryTeacher/{page}/{limit}")
    public R pageQueryTeacher(
            @PathVariable int page,
            @PathVariable int limit,
            @RequestBody LaboratoryAppointment data){
        PageResult pageResult = service.pageQueryTeacher(page, limit, data);
        return R.ok().data("rows",pageResult);
    }

    /**
     * 实验评分
     */
    @PostMapping("sendLaboratoryScore")
    public R sendAppointment(@RequestBody LaboratoryAppointment data){
        service.updateById(data);
        return R.ok();
    }


    /**
     * 删除评估
     */
    @PostMapping("deleteAssess/{id}")
    public R deleteAssess(@PathVariable Integer id){

        LaboratoryAppointment laboratoryAppointmentNew = service.getById(id);


        UpdateWrapper<LaboratoryAppointment> qw = new UpdateWrapper<>();
        qw.set("score",null);
        qw.set("evaluation",null);
        qw.set("teacher_id",null);
        qw.eq("id",id);
        service.update(laboratoryAppointmentNew, qw);
        return R.ok();
    }

    /**
     * 取消预约
     * @param id
     * @return
     */
    @PostMapping("cancelAppointment/{id}")
    public R cancelAppointment(@PathVariable Integer id){
        LaboratoryAppointment laboratoryAppointment = new LaboratoryAppointment();
        laboratoryAppointment.setId(id);
        laboratoryAppointment.setStatus("4");
        service.updateById(laboratoryAppointment);
        return R.ok();
    }

}

