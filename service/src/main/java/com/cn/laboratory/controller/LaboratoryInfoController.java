package com.cn.laboratory.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.laboratory.entity.*;
import com.cn.laboratory.service.LaboratoryAppointmentService;
import com.cn.laboratory.service.LaboratoryInfoScoreService;
import com.cn.laboratory.service.LaboratoryInfoService;
import com.cn.laboratory.utils.page.PageResult;
import com.cn.laboratory.utils.response.R;
import com.cn.laboratory.utils.utils.AceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 实验试题信息表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/laboratory-info")
public class LaboratoryInfoController {

    @Autowired
    private LaboratoryInfoService laboratoryInfoService;

    @Autowired
    private LaboratoryAppointmentService laboratoryAppointmentService;

    @Autowired
    private LaboratoryInfoScoreService laboratoryInfoScoreService;

    private final static String[] sort = {"A","B","C","D","E","F"};

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
            @RequestBody LaboratoryInfo data){
        PageResult pageResult = laboratoryInfoService.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }

    /**
     * 修改操作
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody LaboratoryInfo data){
        // 删除之前数据
        laboratoryInfoService.remove(new QueryWrapper<LaboratoryInfo>().eq("info_id",data.getInfoId()));

        if(data.getType().equals("1")){
            List<LaboratoryInfo> list = data.getList();
            for (int i = 0; i < list.size(); i++) {
                LaboratoryInfo laboratoryInfos = list.get(i);
                // 循环新增试题
                LaboratoryInfo laboratoryInfo = new LaboratoryInfo();
                laboratoryInfo.setLaboratoryId(data.getLaboratoryId());
                laboratoryInfo.setType(data.getType());
                laboratoryInfo.setInfoId(data.getInfoId());
                laboratoryInfo.setTitle(data.getTitle());
                laboratoryInfo.setContent(laboratoryInfos.getContent());
                laboratoryInfo.setFlag(laboratoryInfos.getFlag());
                laboratoryInfo.setSort(sort[i]);
                laboratoryInfo.setTag(data.getTag());
                laboratoryInfoService.save(laboratoryInfo);

            }
        }else{
            // 循环新增试题
            LaboratoryInfo laboratoryInfo = new LaboratoryInfo();
            laboratoryInfo.setLaboratoryId(data.getLaboratoryId());
            laboratoryInfo.setType(data.getType());
            laboratoryInfo.setInfoId(data.getInfoId());
            laboratoryInfo.setTitle(data.getTitle());
            laboratoryInfo.setContent(data.getContent());
            laboratoryInfoService.save(laboratoryInfo);
        }


        return R.ok();
    }

    /**
     * 新增操作
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody LaboratoryInfo data){

        String dddd = AceUtils.stringTimeMillis();
        if(data.getType().equals("1")){ // 添加单选题

            // 查询单选题是否为5道题
            int count = laboratoryInfoService.getCountGroupInfoId(data.getLaboratoryId(), "1");
            if(count >= 5){
                return R.error("添加失败，实验资源单选题已满足5道题");
            }


            List<LaboratoryInfo> list = data.getList();
            for (int i = 0; i < list.size(); i++) {
                LaboratoryInfo laboratoryInfos = list.get(i);
                // 循环新增试题
                LaboratoryInfo laboratoryInfo = new LaboratoryInfo();
                laboratoryInfo.setLaboratoryId(data.getLaboratoryId());
                laboratoryInfo.setType(data.getType());

                laboratoryInfo.setTitle(data.getTitle());
                laboratoryInfo.setContent(laboratoryInfos.getContent());
                laboratoryInfo.setFlag(laboratoryInfos.getFlag());
                laboratoryInfo.setSort(sort[i]);
                laboratoryInfo.setTag(data.getTag());
                laboratoryInfoService.save(laboratoryInfo);

            }
        }else{// 添加简答题

            // 查询简答题是否为5道题
            int count = laboratoryInfoService.getCountGroupInfoId(data.getLaboratoryId(), "2");
            if(count >= 5){
                return R.error("添加失败，实验资源简答题已满足5道题");
            }
            LaboratoryInfo laboratoryInfo = new LaboratoryInfo();
            laboratoryInfo.setLaboratoryId(data.getLaboratoryId());
            laboratoryInfo.setType(data.getType());
            laboratoryInfo.setInfoId(data.getInfoId());
            laboratoryInfo.setInfoId(dddd);
            laboratoryInfo.setTitle(data.getTitle());
            laboratoryInfo.setContent(data.getContent());
            laboratoryInfoService.save(laboratoryInfo);
        }
        return R.ok();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        laboratoryInfoService.removeById(id);
        return R.ok();
    }

    /**
     * 根据实验资源id 查询修改的明细数据
     */
    @GetMapping("getInfoId/{id}")
    public R getInfoId(@PathVariable String id){
        LambdaQueryWrapper<LaboratoryInfo> qw = new LambdaQueryWrapper<>();
        qw.select(LaboratoryInfo::getContent, LaboratoryInfo::getFlag);
        qw.eq(LaboratoryInfo::getInfoId, id);
        List<LaboratoryInfo> list = laboratoryInfoService.list(qw);
        return R.ok().data("row", list);
    }

    /**
     * 根据预约id查询简答题
     */
    @GetMapping("getLaboratoryInfoByAppointmentId/{id}")
    public R getLaboratoryInfoByAppointmentId(@PathVariable Integer id){
        List<LaboratoryInfoScore> list = laboratoryInfoScoreService.getLaboratoryInfoByAppointmentId(id);
        return R.ok().data("list", list);
    }
    /**
     * 提交试卷
     */
    @PostMapping("sendPaper/{userId}/{appointmentId}")
    public R sendScore(@PathVariable("userId") Integer userId, @PathVariable("appointmentId") Integer appointmentId, @RequestBody List<SendPaperVo> sendScoreVo){
        for (SendPaperVo scoreVo : sendScoreVo) {
            String infoId = scoreVo.getInfoId();
            String radioValue = scoreVo.getRadioValue(); // 单选
            String contentValue = scoreVo.getContentValue(); // 试题内容
            List<LaboratoryInfo> hrQuestionInfos = laboratoryInfoService.list(new QueryWrapper<LaboratoryInfo>().eq("info_id", infoId));

            String type = "";
            for (LaboratoryInfo laboratoryInfo : hrQuestionInfos) {
                String id = laboratoryInfo.getId();
                LaboratoryInfoScore laboratoryInfoScore = new LaboratoryInfoScore();
                laboratoryInfoScore.setUserId(userId);
                laboratoryInfoScore.setInfoId(id);
                laboratoryInfoScore.setAppointmentId(appointmentId);
                type = laboratoryInfo.getType();

                // 单选
                if("1".equals(type)){
                    if(id.equals(radioValue)){
                        if(laboratoryInfo.getFlag()){
                            laboratoryInfoScore.setScore(10);
                        }else{
                            laboratoryInfoScore.setScore(0);
                        }
                        laboratoryInfoScoreService.save(laboratoryInfoScore);
                    }
                }else{
                    laboratoryInfoScore.setContent(contentValue);
                    laboratoryInfoScoreService.save(laboratoryInfoScore);
                }
            }
        }

        LaboratoryAppointment laboratoryAppointment = laboratoryAppointmentService.getById(appointmentId);
        laboratoryAppointment.setStatus("2");
        laboratoryAppointment.setLaboratoryTime(new Date());
        laboratoryAppointmentService.updateById(laboratoryAppointment);
        return R.ok();
    }


    /**
     * 提交实验评估
     */
    @PostMapping("sendScore")
    public R score(@RequestBody SendScoreVo sendScoreVo){
        List<LaboratoryInfoScore> list = sendScoreVo.getList();

        for (LaboratoryInfoScore laboratoryInfoScore : list) {
            laboratoryInfoScoreService.updateById(laboratoryInfoScore);
        }
        List<LaboratoryInfoScore> listNew = laboratoryInfoScoreService.list(new QueryWrapper<LaboratoryInfoScore>().eq("appointment_id", sendScoreVo.getAppointmentId()));
        Integer source = 0;
        for (LaboratoryInfoScore laboratoryInfoScore : listNew) {
            source += laboratoryInfoScore.getScore();
        }
        // 更新分数
        LaboratoryAppointment laboratoryAppointment = laboratoryAppointmentService.getById(sendScoreVo.getAppointmentId());
        laboratoryAppointment.setEvaluation(sendScoreVo.getEvaluation());
        laboratoryAppointment.setTeacherId(sendScoreVo.getTeacherId());
        laboratoryAppointment.setScore(source);
        laboratoryAppointmentService.updateById(laboratoryAppointment);
        return R.ok();
    }

}

