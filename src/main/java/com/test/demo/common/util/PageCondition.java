package com.test.demo.common.util;

import lombok.Data;

import java.io.Serializable;

/**
* @Description: 分页查询条件
* @Author: 王刚
* @Date: 2018/5/15 0016
*/ 
@Data
public class PageCondition implements Serializable {
    //当前页码
    private int page;
    //每页显示
    private int count;
    //小车编号&地标卡编号&物料编号&小车plc的ip&控制柜编号
    private String id;
    //故障类型&小车类型:SPS,AKW
    private String type;
    //开始时间
    private String date;
    //结束时间
    private String endTime;
    //物料名称&产线名称
    private String name;

    private String state;

    private String taskState;


}
