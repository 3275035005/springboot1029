package com.cn.laboratory.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.laboratory.entity.LaboratoryAppointment;
import com.cn.laboratory.service.LaboratoryAppointmentService;
import com.cn.laboratory.utils.utils.AceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 定时任务
 */
@Component
@EnableScheduling
public class Task {

    @Autowired
    private LaboratoryAppointmentService laboratoryAppointmentService;

    /**
     * 定时任务1秒执行一次
     */
    @Scheduled(cron = "*/1 * * * * ?")
    public void cron(){
        // 查询实验预约
        List<LaboratoryAppointment> list = laboratoryAppointmentService.list(new QueryWrapper<LaboratoryAppointment>().in("status", "0", "1"));
        for (LaboratoryAppointment laboratoryAppointment : list) {
            // (预约时间 - 40分钟) <= 当前时间 <= 预约时间
            if(!StringUtils.isEmpty(laboratoryAppointment.getAppointmentTime())){
                Date date = AceUtils.zhCurrentTime(laboratoryAppointment.getAppointmentTime());
                long thisTime = new Date().getTime();
                long end40 =  date.getTime() + 2400000;

                if(thisTime >= end40){ // 当前时间大于 预约时间+40分钟 修改为 实验已结束
                    laboratoryAppointment.setStatus("3");
                } else {
                    if(thisTime >= date.getTime()){
                        laboratoryAppointment.setStatus("1");
                    }
                }
                laboratoryAppointmentService.updateById(laboratoryAppointment);
            }

        }


    }

}
