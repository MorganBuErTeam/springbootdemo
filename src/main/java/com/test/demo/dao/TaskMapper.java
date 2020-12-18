package com.test.demo.dao;

import com.test.demo.common.util.PageCondition;
import com.test.demo.common.vo.UpOrDown;
import com.test.demo.domain.Task;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;


public interface TaskMapper extends Mapper<Task> {

	/**
	 * Title: insertEntity
	 * Description: 新增任务信息返回主键值
	 * @param entity
	 */
//	public Integer insertEntity(Task entity);
	/**
	 * 根据任务类型，起始站点以及目标站点是否存在未完成的最早的任务信息 
	 * @param startSitCode 起始站点编号
	 * @param targetSitCode 目标站点编号
	 * @param type 任务类型
	 * @return TaskLog
	 */
	public Task queryTaskLogInfoByWork(int startSitCode, int targetSitCode, String type);
	
	/**
	 * 查询最早的未完成的任务日志
	 * @return TaskLogLog
	 */
	public Task queryFristTaskLogNotStart();
	
	/**
	 * 有关任务查询
	 * @param param
	 * date：最晚发车时间区间查询的起始时间
	 * endTime：最晚发车时间区间查询的结束时间
	 * offset：当前页
	 * limit：每页展示条数
	 * @return List<TaskLog>
	 */
	public List<Task> queryTaskInfoBy(PageCondition param);

	public List<Task> queryTaskBy(@Param("state") String state, @Param("taskType") String taskType, @Param("date") String date, @Param("endTime") String endTime);


	public int queryTaskCountBy(PageCondition param);

	/**
	 * 根据响应时间的时间段导出Excel文件
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Task> queryInfoBy(@Param("state") String state, @Param("type") String type, @Param("start") Date startDate, @Param("end") Date endDate);

	/**
	 * 修改任务状态
	 * @param code
	 * @param state
	 */
	public void updateTaskState(@Param("id") String code, @Param("state") int state);


	/**
	 * 上移下移
	 * @param type 1：上移，2：下移
	 * @param entitys 当前对象
	 */
	public void updateUpOrDown(@Param("list") List<UpOrDown> entitys);


	/**
	 * 修改未执行的任务状态为完成
	 */
	public void updateNotExecute();


	/**
	 * 查询未执行的任务
	 * @return
	 */
	public List<Task> queryNotExecute();


	public String selectConfirBargeCount();
	
	/**
	 * 根据sort查询id
	 * @param sort 
	 * @return int
	 */
	public int selectIdBySort(int sort);
	
	/**
	 * 查询最大主键值
	 * @return
	 */
	public Integer selectMaxSort();
	
	/**
	 * 新增任务日志信息
	 * @param entitys
	 */
	public void insertTaskLog(List<Task> entitys);

	//生成任务前，查询任务表中是否有此任务
	public int quereyByTask(String type);

	public Task quereyTask(Task task);


}
