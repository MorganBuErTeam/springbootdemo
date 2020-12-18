package com.test.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.demo.common.service.impl.AbstractBaseService;
import com.test.demo.common.vo.smt.SmtInfoVo;
import com.test.demo.constant.Constant;
import com.test.demo.dao.MesInfoMapper;
import com.test.demo.domain.MesInfo;
import com.test.demo.httpclient.HttpAPIService;
import com.test.demo.service.HttpClientService;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class HttpClientServiceImpl extends AbstractBaseService<MesInfoMapper, MesInfo> implements HttpClientService {

	Logger logger = LoggerFactory.getLogger(HttpClientServiceImpl.class);

	@Autowired
	HttpAPIService httpApiService;
	@Autowired
	MesInfoMapper mesInfoMapper;

	//	/**
//	 * 请求mes接任务
//	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public MesInfo requestMesToGetJob() throws Exception {
		try {
			String url = "http://192.168.1.100:5000/api/agv/agvtask";
			String data = httpApiService.doGet(url);
//			logger.info(Constant.SDF.format(new Date())+":mes返回,"+data);
			if (data != null) {
				JSONObject parseObj = JSONObject.parseObject(data);
				String result = parseObj.getString("results");
				String code = parseObj.getString("code");
				if ("500".equals(code)) {
					Thread.sleep(1000);
					return null;
				}
				JSONObject parseObject = JSONObject.parseObject(result);
				if (parseObject==null) {
					return null;
				}
				String taskId = parseObject.getString("taskId");
				String taskType = parseObject.getString("taskType");
				String takeGoodCode = parseObject.getString("takeGoodCode");
				String dischargingCode = parseObject.getString("dischargingCode");
				String requestTime = parseObject.getString("requestTime");
				MesInfo mesInfo = new MesInfo();
				mesInfo.setTaskId(Integer.parseInt(taskId));
				mesInfo.setTaskType(taskType);
				mesInfo.setTakeGoodCode(takeGoodCode);
				mesInfo.setDischargingCode(dischargingCode);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date date = formatter.parse(requestTime);
				long time = date.getTime();
				mesInfo.setCode("mes" + time);
				mesInfo.setRequestTime(date);
				mesInfo.setStatus("01");
				mesInfo.setCreateDate(new Date());
				mesInfo.setJsonMessageInfo(data);


			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	//post传参
	private String smtMater(List<T> materielMasters, String modifyType) throws Exception {
		String tempType = "";
		String data = null;
		HashMap<String, Object> reply = new HashMap<>();
		if (modifyType.equals("0")) {
			tempType = "新增";
		} else if (modifyType.equals("1")) {
			tempType = "修改";
		} else if (modifyType.equals("2")) {
			tempType = "删除";
		}
		reply.put("modifyType", modifyType); //0-新增 1-修改 2-删除
		reply.put("requestTime", Constant.SDF.format(new Date()));
		reply.put("data", materielMasters); //以集合传参
		//发送请求
		data = httpApiService.httpPostWithJsonObject("url", (JSONObject) JSONObject.toJSON(reply));
		logger.info("smt物料" + tempType + ",wms请求参数:" + reply.toString() + ";smt返回参数:" + data);
		if (null == data) {
			logger.info(Constant.SDF.format(new Date()) + ":物料" + tempType + ",smt系统返回null");
			throw new RuntimeException();
		}
		//解析smt返回数据
		String replace = data.replace("\\", "");
		String jsondata = replace.substring(1, replace.length() - 1);
		JSONObject parseObj = JSONObject.parseObject(jsondata);
		String code = parseObj.getString("statusCode");
		//持久化交互数据

		if ("01".equals(code)) {
			logger.info(Constant.SDF.format(new Date()) + ":物料" + tempType + ",smt系统异常详情:" + parseObj.getString("exptMsgDetail"));
			throw new RuntimeException();
		}
		return null;
	}

	//post无参,解析json数组
	private List<SmtInfoVo> smtInfo() throws Exception {
		String data = null;
		List<SmtInfoVo> smtInfoVoList = new ArrayList<>();
		SmtInfoVo smtInfoVo=null;
		data = httpApiService.doGet("url");
		if (data == null) {
			logger.info(Constant.SDF.format(new Date()) + ":请求smt实时信息,smt系统返回null");
			return null;
		}
		//{"data":[{"rackNumber":"L0001","communicationStatus":"正常","equipmentStatus":"正常"},{"rackNumber":"L0002","communicationStatus":"故障","equipmentStatus":"故障"}],"statusCode":"00","errorMessage":"success","exptMsgDetail":null}
		logger.info("获取smt实时信息,请求参数:无;返回参数:" + data);
		//解析返回
		JSONObject str = JSONObject.parseObject(data);
		String statusCode=str.getString("statusCode");
		//持久化数据

		if ("00".equals(statusCode)) {
			//todo 重点:data是一个json数组
			JSONArray tr = str.getJSONArray("data");
			//对数组元素进行遍历
			for (int i = 0; i < tr.size(); i++) {
				smtInfoVo=new SmtInfoVo();
				String t1 = tr.getString(i);
				JSONObject t2 = JSONObject.parseObject(t1);
				smtInfoVo.setRackNumber(t2.getString("rackNumber"));
				smtInfoVo.setEquipmentStatus(t2.getString("equipmentStatus"));
				smtInfoVo.setCommunicationStatus(t2.getString("communicationStatus"));
				smtInfoVoList.add(smtInfoVo);
			}
		} else {
			logger.info("获取smt实时信息,返回异常详情:" + str.getString("exptMsgDetail"));
			return null;
		}
		return smtInfoVoList;
	}




	@Override
	public void insert(MesInfo entity) {

	}

	@Override
	public void insertSelective(MesInfo entity) {

	}


}