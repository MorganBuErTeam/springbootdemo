package com.test.demo.service.impl;

import com.test.demo.common.data.PageBean;
import com.test.demo.common.service.impl.AbstractBaseService;
import com.test.demo.common.util.DateUtil;
import com.test.demo.common.util.PageCondition;
import com.test.demo.common.util.Query;
import com.test.demo.common.vo.UpOrDown;
import com.test.demo.dao.TaskMapper;
import com.test.demo.domain.Task;
import com.test.demo.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.*;


@Service
public class TaskServiceImpl extends AbstractBaseService<TaskMapper,Task> implements TaskService {

	Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private RedisTemplate<String,  String> redisTemplate;

	private RedisConnection execute() {
		return (RedisConnection) redisTemplate.execute(new RedisCallback() {
			@Override
			public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
				return redisConnection;
			}
		});
	}

	@Override
	public void test(){
		String mykey=redisTemplate.opsForValue().get("mykey");
		redisTemplate.opsForValue().set("mykey","rty");
		redisTemplate.opsForValue().set("task","test2");

		System.out.println("service结束");
	}
	
	@Override
	public Task queryPage(String wmsTaskId) {
		List<Task> result = super.selectByExample("wmsTaskId", wmsTaskId);
		return result.size()==0?null:result.get(0);
	}
	
	 
	@Override
	public PageBean<Task> queryPage(PageCondition pageCondition) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", pageCondition.getPage());
			params.put("limit", pageCondition.getCount());
			params.put("isOrderBy",true);
			params.put("orderBy","real_start_date desc");

			if (StringUtils.isNotBlank(pageCondition.getType()) && !"null".equals(pageCondition.getType())) {
				if("1".equals(pageCondition.getType())){
					params.put("taskType",""); //todo
				}else if("2".equals(pageCondition.getType())){
					params.put("taskType","");
				}
				params.put("state",2);
			}

			//按照实际的发车时间查询
			if (StringUtils.isNotBlank(pageCondition.getDate()) && !"null".equals(pageCondition.getDate())) {
				params.put("startDate", pageCondition.getDate() + " 00:00:00");
				if (StringUtils.isNotBlank(pageCondition.getEndTime()) && !"null".equals(pageCondition.getEndTime())) {
					params.put("isQueryByDate", true);//时间段查询
					params.put("name", "realStartDate");
					params.put("endDate", pageCondition.getEndTime() + " 23:59:59");
				}
			}
			Query query = new Query(params);

			return super.selectByQuery(query);
		} catch (Exception e) {
			this.logger.error("数据查询失败："+e.getMessage());
			return null;
		}
	}

	@Override
	public PageBean<Task> historyTaskQuery(PageCondition pageCondition) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", pageCondition.getPage());
			params.put("limit", pageCondition.getCount());
			params.put("isOrderBy",true);
			params.put("orderBy","real_start_date desc");
			if (StringUtils.isNotBlank(pageCondition.getState()) && !"null".equals(pageCondition.getState())) {
				params.put("state",pageCondition.getState());
			}
			if (StringUtils.isNotBlank(pageCondition.getType()) && !"null".equals(pageCondition.getType())) {
				params.put("taskType",pageCondition.getType());
			}
			if (StringUtils.isNotBlank(pageCondition.getTaskState()) && !"null".equals(pageCondition.getTaskState())) {
				int temp=0;
				//3:完成 4.删除(非物理) 5:取消
				if("取消".equals(pageCondition.getTaskState())){
					temp=5;
				}else if("完成".equals(pageCondition.getTaskState())){
					temp=3;
				}else if("删除".equals(pageCondition.getTaskState())){
					temp=4;
				}
				params.put("state",temp);
			}

			//按照实际的发车时间查询
			if (StringUtils.isNotBlank(pageCondition.getDate()) && !"null".equals(pageCondition.getDate())) {
				params.put("startDate", pageCondition.getDate() + " 00:00:00");
				if (StringUtils.isNotBlank(pageCondition.getEndTime()) && !"null".equals(pageCondition.getEndTime())) {
					params.put("isQueryByDate", true);//时间段查询
					params.put("name", "createDate");
					params.put("endDate", pageCondition.getEndTime() + " 23:59:59");
				}
			}
			Query query = new Query(params);

			return super.selectByQuery(query);
		} catch (Exception e) {
			this.logger.error("数据查询失败："+e.getMessage());
			return null;
		}
	}


    @Override
    public List<Task> workQuery(String type) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(type) && !"null".equals(type)) {
                if("1".equals(type)){
                    params.put("taskType","");
                }else if("2".equals(type)){
                    params.put("taskType","");
                }
                params.put("state",2);
            }

            Query query = new Query(params);
            Class<Task> clazz = (Class<Task>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
            Example example = new Example(clazz);
            Example.Criteria criteria = example.createCriteria();
            if(query.entrySet().size()>0) {
                for (Map.Entry<String, Object> entry : query.entrySet()) {
                    if (query.isLike()) {
                        criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                        continue;
                    }
                    criteria.andEqualTo(entry.getKey(), entry.getValue());
                }
            }
            List<Task> list = mapper.selectByExample(example);
            return list;
        } catch (Exception e) {
            this.logger.error("数据查询失败："+e.getMessage());
            return null;
        }
    }

	@Override
	public List<Task> taskQuery(String state, String type,String date ,String endTime){
		try {
			Date date1=null;
			Date date2=null;
//			if(!"".equals(date) && null!=date){
//				date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
//			}
//			if(!"".equals(endTime) && null!=endTime){
//				date2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
//			}
			List<Task> tasks=taskMapper.queryTaskBy(state,type,date,endTime);
			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String saveTask(Task task) throws Exception {
		String msg = null;//排重结果
		try {
            //拿任务类型,起始点和目的点,状态是1和2,查重
			task.setState(1);
            Task entity=taskMapper.quereyTask(task);
            if(entity!=null){
                return "任务已存在";
            }
            //TODO 任务生成
			super.insert(task);


		} catch (Exception e) {
			msg = e.getMessage();
			logger.error("任务数据新增失败："+ msg);
			throw new Exception(e.getMessage());
		}
		return msg;
	}

	@Override
	public PageBean<Task> queryTaskInfoBy(PageCondition param) {
		try {
			if (StringUtils.isEmpty(param.getType()) || param.getType().equals("null")) {
				param.setType(null);
			}
			if (StringUtils.isEmpty(param.getDate()) || param.getDate().equals("null")) {
				param.setDate(null);
			}else{
				param.setDate(param.getDate() + " 00:00:00");
				if (!StringUtils.isEmpty(param.getEndTime())) {
					param.setEndTime(param.getEndTime() + " 23:59:59");
				}else {
					param.setEndTime(null);
				}
			}
			param.setPage((param.getPage() -1)*param.getCount());
			PageBean<Task> res = new PageBean<>(taskMapper.queryTaskInfoBy(param), taskMapper.queryTaskCountBy(param));
			return res;
		} catch (Exception e) {
			logger.error("任务数据查询失败："+ e.getMessage());
			return null;
		}
	}
	
	
	@Override
	public List<Task> queryInfoBy(String state,String type,String startDate, String endDate) {
		try {
			Date startTime = null;
			Date endTime = null;
			if (StringUtils.isNotBlank(startDate) && !"null".equals(startDate)) {
				startTime = DateUtil.stringToDate((startDate+" 00:00:00"));
				if (StringUtils.isNotBlank(endDate) && !"null".equals(endDate)) {
					endTime = DateUtil.stringToDate((endDate+" 23:59:59"));
				}
			}
			return taskMapper.queryInfoBy(state,type,startTime, endTime);
		} catch (Exception e) {
			logger.error("小车里程数据查询失败："+e.getMessage());
			return null;
		}
		
	}


	@Override
	public String updateTaskState(String id, int state) throws Exception {
		String msg = null;// 返回信息
		try {
			taskMapper.updateTaskState(id, state);
 		} catch (Exception e) {
			msg = e.getMessage();
			this.logger.error("任务状态修改失败：" + msg);
			throw new Exception(msg);
		}
		return msg;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public String updateUpOrDown(int type, int id , int sort) throws Exception{
		try {
			if (id != 0) {
				List<UpOrDown> entitys = new ArrayList<UpOrDown>();
				int orther = (type == 1 ? sort -1 : sort + 1);
				UpOrDown one = new UpOrDown(type, id , orther);
				entitys.add(one);
				UpOrDown two = new UpOrDown((type == 1 ? 2 : 1),taskMapper.selectIdBySort(orther),sort);
				entitys.add(two);
				taskMapper.updateUpOrDown(entitys);
 			}
		} catch (Exception e) {
			logger.error("站点数据新增失败：" + e.getMessage());
			throw new Exception(e.getMessage());
		}
		return null;
	}


	@Override
	public void updateNotExecute() throws Exception {
		taskMapper.updateNotExecute();
	}


	@Override
	public List<Task> queryNotExecute() {
		return taskMapper.queryNotExecute();
	}


	@Override
	public Map<String, Object> selectConfirBarge(int page, int count) {
		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("rows", taskMapper.selectConfirBarge((page -1)*count,count));
		result.put("total", taskMapper.selectConfirBargeCount());
 		return result;
	}

	@Override
	public void insert(Task entity) {

	}

	@Override
	public void insertSelective(Task entity) {

	}
}
