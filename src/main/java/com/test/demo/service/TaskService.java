package com.test.demo.service;

import com.test.demo.common.data.PageBean;
import com.test.demo.common.service.BaseService;
import com.test.demo.common.util.PageCondition;
import com.test.demo.dao.TaskMapper;
import com.test.demo.domain.Task;

import java.util.List;
import java.util.Map;


public interface TaskService extends BaseService<TaskMapper,Task> {

	public void test();
	 
	/**
	 * 分页查询任务信息 
	 * @param pageCondition
 	 * @return PageBean<Avoid>
	 */
	public PageBean<Task> queryPage(PageCondition pageCondition);

	public PageBean<Task> historyTaskQuery(PageCondition pageCondition);

	public List<Task> workQuery(String type);


	public List<Task> taskQuery(String state, String type, String date, String endTime);


	/**
	 * 根据wmsTaskId查询任务日志
	 * @param wmsTaskId
	 * @return Task
	 */
	public Task queryPage(String wmsTaskId);


	/**
	 * 新增任务信息
	 * @param
	 * @return String
	 */
	public String saveTask(Task entity) throws Exception;

	/**
	 * 有关当前任务与任务历史的查询
	 * @param param
	 * type ： 决定是历史任务还是当前任务
	 * type != null 是历史任务，反之是当前任务
	 * date：最晚发车时间区间查询的起始时间
	 * endTime：最晚发车时间区间查询的结束时间
	 * offset：当前页
	 * limit：每页展示条数
	 * @return List<Task>
	 */
	public PageBean<Task> queryTaskInfoBy(PageCondition param);

	/**
	 * 根据响应时间的时间段导出Excel文件
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Task> queryInfoBy(String state, String type, String startDate, String endDate);


	/**
	 * 修改任务状态
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public String updateTaskState(String id, int state) throws Exception;


	/**
	 * 上移下移
	 * @param type 1：上移，2：下移
	 * @param id 当前对象
	 */
	public String updateUpOrDown(int type, int id, int sort) throws Exception;
	
	 
	/**
	 * 修改未执行的任务的状态为完成
	 * @throws Exception
	 */
	public void updateNotExecute()throws Exception;
		
	/**
	 * 查询未执行的任务
	 * @return
	 */
	public List<Task> queryNotExecute();
	
	
	
	/**
	 * 查询驳运信息内容
	 * @return
	 */
	public Map<String, Object> selectConfirBarge(int page, int count);
	
	
 
}
