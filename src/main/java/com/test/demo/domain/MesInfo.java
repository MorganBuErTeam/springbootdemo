package com.test.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "t_mes_info")
@AllArgsConstructor//有参构造
public class MesInfo {

	@Id
	private int id;// 编号（自增）

	@Column(name = "task_id")
	private int taskId;// 任务id

	@Column(name = "code")
	private String code; // 编号（字母+数）
	
	@Column(name = "request_Time")
	private Date requestTime;// 请求时间

	@Column(name = "sys_task_id")
	private int sysTaskId;// t_task id的表
	
	@Column(name = "takeGood_Code")
	private String takeGoodCode;// 取货编号

	@Column(name = "discharging_Code")
	private String dischargingCode;// 送货编号

	@Column(name = "task_type")
	private String taskType;// 任务类型(1-上料,下料)

	@Column(name = "json_message_info")
	private String jsonMessageInfo;// json文件

	@Column(name = "status")
	private String status;// 状态(1-处理成功，2-处理失败)
	
	@Column(name = "update_date")
	private Date updateDate;//更新时间
	
	@Column(name = "create_date")
	private Date createDate;//新增时间

	public MesInfo() {
		super();
	}
	
	

}
