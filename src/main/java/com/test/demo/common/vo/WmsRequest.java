package com.test.demo.common.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WmsRequest {
	private String skipCode; 	//料车编号,多个以逗号隔开
	private String lineName; //产线名称
	private String workCode; // 配送工位编号
	private Date lastPutTime; //最晚配送时间
	private String materialName; // 物料名称
	private int materialCount;  //物料数量
	private int state;			//1未执行，2.取消，3.执行中，4.完成  5.强制完成  6. 删除

}
