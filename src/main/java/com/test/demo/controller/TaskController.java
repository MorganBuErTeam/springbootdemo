package com.test.demo.controller;

import com.test.demo.common.controller.BaseController;
import com.test.demo.common.data.PageBean;
import com.test.demo.common.util.PageCondition;
import com.test.demo.common.util.ResponseVoUtil;
import com.test.demo.common.vo.ResponseVo;
import com.test.demo.dao.TaskMapper;
import com.test.demo.domain.Task;
import com.test.demo.service.TaskService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 任务信息
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/TaskInfo")
@Slf4j
public class TaskController extends BaseController<TaskService,Task> {

    @Autowired
    private TaskService taskService;
	@Autowired
	private TaskMapper taskMapper;


    /**
     * 分页查询任务信息
     * @param pageCondition
     * @return
	 * ApiOperation注解，注释api
     */
    @PostMapping("/query")
	@ApiOperation(value="分页查询任务信息", notes="任务信息")
    public PageBean<Task> queryPage(PageCondition pageCondition){
    	 return this.taskService.queryPage(pageCondition);
    }


	/**
	 * 分页查询历史任务信息
	 * @param pageCondition
	 * @return
	 */
	@PostMapping("/historyTaskQuery")
	public PageBean<Task> historyTaskQuery(PageCondition pageCondition){
		return this.taskService.historyTaskQuery(pageCondition);
	}


	@PostMapping("/workQuery")
	public List<Task> queryPage(String type){
		return this.taskService.workQuery(type);
	}

    
    /**
     *
     * @param
     * @return
     */
    @PostMapping("/taskAndHisTask")
	public PageBean<Task> getTaskQueue(PageCondition PageCondition) {
 		return taskService.queryTaskInfoBy(PageCondition);
	}


	@PostMapping("/taskQuery")
	public List<Task> taskQuery(@RequestParam("state")String state, @RequestParam("type")String type, @RequestParam("date")String date ,@RequestParam("endTime")String endTime){
    	return this.taskService.taskQuery(state,type,date,endTime);
	}


	/**
     * 查询未执行的任务
     * @return
     */
    @PostMapping(value = "/queryNotExecute")
	public List<Task> queryNotExecute() {
		return taskService.queryNotExecute();
	}
    
    /**
     * 查询驳运信息
     * @return
     */
    @PostMapping(value = "/queryConfirBarge")
	public Map<String, Object> queryConfirBarge(@RequestParam("page")int page,@RequestParam("count") int count) {
		return taskService.selectConfirBarge(page,count);
	}
    
    
    /**
     * 新增任务信息
     * @param
     * @return
     */
    @PostMapping("/save")
    public ResponseVo saveControlCabinetInfo(@RequestBody Task entity) {
        String msg = null;
		try {
//			Task entity=new Task();
//			entity.setType("内部转运");
//			entity.setStartSitCode(1);
//			entity.setTargetSitCode(1);
//			entity.setFrequency("有限循环");
//			entity.setCount("5");
			msg = this.taskService.saveTask(entity);
		} catch (Exception e) {
			msg = e.getMessage();
		}
        return msg == null ? ResponseVoUtil.successMsg("保存成功") : ResponseVoUtil.failResult("保存失败," + msg);
    }
    
    /**
     * 修改任务状态
     * @param
     * @param state
     * @return
     */
	@PostMapping("/updateTaskState")
	public ResponseVo updateTaskState(@RequestParam("id") String id, @RequestParam("state") int state) {
		String msg = null;
		try {
			msg = this.taskService.updateTaskState(id, state);
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg == null ? ResponseVoUtil.successMsg("修改成功") : ResponseVoUtil.failResult("修改失败" + msg);
	}
    
	
	/**
	 * 上移下移
	 * @param type 1：上移，2：下移
	 * @param id 当前对象
	 */
	@PostMapping(value = "/updateUpOrDown")
   public ResponseVo updateUpOrDown(@RequestParam("type") int type, @RequestParam("id") int id,@RequestParam("sort") int sort) {
		String msg = null;
		try {
			msg = taskService.updateUpOrDown(type, id , sort);
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg == null?ResponseVoUtil.successMsg("更新成功!"):ResponseVoUtil.failResult(msg+",更新失败!");
   }  
	 
	
}