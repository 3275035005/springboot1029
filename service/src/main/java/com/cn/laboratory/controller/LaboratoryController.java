package com.cn.laboratory.controller;


import com.cn.laboratory.entity.Laboratory;
import com.cn.laboratory.entity.LaboratoryInfo;
import com.cn.laboratory.service.LaboratoryAppointmentService;
import com.cn.laboratory.service.LaboratoryInfoService;
import com.cn.laboratory.service.LaboratoryService;
import com.cn.laboratory.utils.page.PageResult;
import com.cn.laboratory.utils.response.R;
import com.cn.laboratory.utils.utils.AceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实验资源 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/laboratory")
public class LaboratoryController {

    @Autowired
    private LaboratoryService service;

    @Autowired
    private LaboratoryAppointmentService laboratoryAppointmentService;

    @Autowired
    private LaboratoryInfoService laboratoryInfoService;

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
            @RequestBody Laboratory data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }

    /**
     * 修改操作
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody Laboratory data){

        service.updateById(data);
        return R.ok();
    }

    /**
     * 新增操作
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody  Laboratory data){
        // 添加实验
        service.save(data);


        return R.ok();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable Integer id){

        // 删除实验
        service.removeById(id);
        return R.ok();
    }

    /**
     * 通过实验Id查询实现详情数据
     * @param id
     * @return
     */
    @GetMapping("getLaboratoryById/{id}")
    public R getLaboratoryById(@PathVariable Integer id){
        Laboratory laboratory = service.getById(id);
        return R.ok().data("row", laboratory);
    }

    /**
     * 通过实验Id查询实现详情数据
     * @return
     */
    @GetMapping("getLaboratoryAll")
    public R getLaboratoryAll(){
        List<Laboratory> list = service.list(null);
        return R.ok().data("row", list);
    }

    /**
     * 通过实验ID查询实验视频 和 实验试题
     */
    @GetMapping("getLaboratoryProcedureById/{id}")
    public R getLaboratoryProcedureById(@PathVariable Integer id){
        Laboratory laboratory = service.getById(id);
        Map<String, Object> row = new HashMap<>();
        row.put("videoPath", laboratory.getVideoPath());
        List<LaboratoryInfo> list = laboratoryInfoService.getLaboratoryInfoById(id);
        row.put("list", list);

        return R.ok().data("row", row);
    }


    /**
     * 查询首页
     *  1. 统计柱状图
     *  2. 实验资源科目占比
     *  3. 实验预约科目占比
     * @return
     */
    @GetMapping("getZzt/{type}")
    public R getZzt(@PathVariable String type){

        Map<String, Object> map = new HashMap<>();
        if(type.equals("1")){
            List<String> day = new ArrayList<>(12);
            List<Integer> number = new ArrayList<>(12);
            // 获取月份天数
            for(int i=1;i<=12;i++){
                day.add(i + "月");
                number.add(0);
            }
            List<Map<String, Object>> datas = laboratoryAppointmentService.getZzt(AceUtils.getYear(), 7);
            if(datas != null && datas.size()>0){
                for (Map<String, Object> data : datas) {
                    if(data != null){
                        System.out.println(data);
                        if(StringUtils.isNotEmpty(String.valueOf(data.get("day")))){
                            int ss = AceUtils.getDateOfMonth((String) data.get("day"));
                            number.remove(ss-1);
                            number.add(ss-1, Integer.parseInt(String.valueOf(data.get("number"))));
                        }
                    }
                }
            }

            map.put("day", day);
            map.put("number", number);
        }else if(type.equals("2")){

            List<String> day = new ArrayList<>(31);
            List<Integer> number = new ArrayList<>(31);
            // 获取月份天数
            int month = AceUtils.getYesterday(AceUtils.getThisMonth());
            System.out.println(month);
            for(int i=1;i<=month;i++){
                day.add(i+"号");
                number.add(0);
            }
            List<Map<String, Object>> datas = laboratoryAppointmentService.getZzt(AceUtils.getThisMonth(), 10);
            if(datas != null && datas.size()>0){
                for (Map<String, Object> data : datas) {
                    if(data != null){
                        if(StringUtils.isNotEmpty(String.valueOf(data.get("day")))){
                            int ss = AceUtils.getDateOfDay((String) data.get("day"));
                            number.remove(ss-1);
                            number.add(ss-1, Integer.parseInt(String.valueOf(data.get("number"))));
                        }
                    }
                }
            }

            map.put("day", day);
            map.put("number", number);
        }
        // 2. 实验资源科目占比
        List<Map<String, Object>> materialsList = service.getZb();
        map.put("laboratoryList", materialsList);
        // 3. 实验预约科目占比
        List<Map<String, Object>> orderList = laboratoryAppointmentService.getZb();
        map.put("appointmentList", orderList);

        return R.ok().data("data", map);
    }

}

