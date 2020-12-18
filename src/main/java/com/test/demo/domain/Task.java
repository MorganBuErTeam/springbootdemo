package com.test.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.*;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 任务信息对象
 *
 *
 */
@Data
@Table(name = "t_task")
@Accessors(chain = true)
@NoArgsConstructor //无参构造
@AllArgsConstructor//有参构造
public class Task implements Cloneable,Serializable {
	
	@Id
	private Integer id;// 编号（自增）

	@Column(name = "agvCode")
	private Integer agvCode; //agv编号

	@Column(name = "taskName")
	private String taskName;//任务名称

	@Column(name = "state")
	private Integer state;// 任务状态: 1:未执行  2:执行中  3:完成 4.删除(非物理) 5:取消

	@Column(name = "taskType")
	private String taskType;//任务类型

	@Column(name = "create_date")
	private Date createDate;// 产生时间

	@Column(name = "real_start_date")
	private Date realStartDate;//实际发车时间/响应时间

	@Column(name = "finish_date")
	private Date finishDate;// 完成时间

	@Column(name = "remark")
	private String remark;// 备注

	@Column(name = "sort")
	private Integer sort;// 优先级

	@Column(name = "count")
	private String count;// 搬运次数

	@Column(name = "finishCount")
	private String finishCount;// 完成次数

	@Column(name = "frequency")
	private String frequency;// 任务频率

	@Column(name = "start_sit_code")
	private String startSitCode;// 起始停靠点
	
	@Column(name = "target_sit_code")
	private String targetSitCode;// 目标停靠点



	@Override
	public Object clone() {
		Task task = null;
		try {
			task = (Task) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return task;
	}




}
