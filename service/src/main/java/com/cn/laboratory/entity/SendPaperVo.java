package com.cn.laboratory.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SendPaperVo {


    /**
     * 试题id
     */
    private String infoId;

    /**
     * 单选值
     */
    private String radioValue;

    /**
     * 简答题内容
     */
    private String contentValue;
}
