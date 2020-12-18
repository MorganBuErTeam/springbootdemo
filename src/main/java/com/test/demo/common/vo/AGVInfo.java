package com.test.demo.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.demo.netty.AGVTCPBaseComm;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.*;

/**
 * 小车实时信息与数据库存储信息的容器
 * 
 *
 */
@Data
// @Component
public class AGVInfo implements Serializable, Runnable {

	Logger logger = LoggerFactory.getLogger(AGVInfo.class);

//	public AGVInfo(GetInitSource soureInfo) {
//		super();
//		this.soureInfo = soureInfo;
//	}
	private final int area = 10;// 容错区间
	private Date connTime;// 连接时间
	private Date stopTime;// 断开时间
	private int connCount;// 连接次数
	private String state;// 小车状态
	private Date updateTime;// 最新刷新时间
	private double mapX;// 站点位置x
	private double mapY;// 站点位置y
	private double angle;// 角度
	private int siteCode;// 地标卡号,排重

//	private AGV agv;// 小车基本信息
//	private AGVPLCparamInfo agvRealInfo;// 小车实时信息
//	private AGVNavData agvNavData;// 小车位置信息
	
//	@JsonIgnore
//	@Transient
//	private GetInitSource soureInfo;// 配置相关数据信息
	@JsonIgnore
	@Transient
	private AGVTCPBaseComm client;// 通讯对象
	@JsonIgnore
	@Transient
	private Map<String, Long> handleMap = new HashMap<String, Long>();// 小车结构体属句柄值
	private List<byte []> taskPathList = new ArrayList<byte []>();// 任务路径信息
	private int doTaskId;// 小车正在执行的大任务
	private int[] doTaskLocationIdArr = { 0, 0, 0 };// 小车正在执行的子任务id
//	private String [] doTaskName;// 小车执行的的所有步任务名称
	private int taskType = -1;//任务类型，1.送满，2送空
	private int taskPickSit = -1;// 任务取货点
	private int taskTargetSit = -1;// 任务放货点
	private int taskAllCount;// 当前子任务要下发的任务路径数
	private int nowDoTaskNum;// 当前子任务已下发的任务路径数
	private boolean avoidStopCar = false;// 避让停车标志
	private int freeTime = 0;//小车实际闲置时间
	private String rids;//小车当前任务对应的路径id，多个以逗号分隔
	private int routeStartSit;//路径起点
//	private Task task;//小车当前执行的大任务信息
	private boolean notToTaskSit = false;//上料装配&送至烘干送空任务不去到烘箱任务点直接去任务取车点取
//	private int sitCodeDoorStop = 0;//小车经过的卷帘门停车点

	private int[] pathLastSitCode;  //子任务，每条路径的最后一个站点
	private int pathNum; //子任务正在执行第几条路径
	private int pathSum; //子任务总路径数
	int num = 1;//记录小车空闲时间
//	TaskLocation taskDetail =null;
	/**
	 * Title: clearTask
	 * Description: 清空小车任务信息
	 */
	public void clearTask(){
		taskPathList.clear();//清空任务
		int [] taskNum = new int[3];
		for (int i = 0; i < taskNum.length; i++) {
			taskNum[i] = 0;
		}
		setDoTaskLocationIdArr(taskNum);//清空任务id
		setTaskPickSit(-1);
		setTaskTargetSit(-1);
		setTaskType(-1);
		setTaskAllCount(0);
		setNowDoTaskNum(0);
		setDoTaskId(-1);
		setRouteStartSit(-1);
		setRids(null);
//		setTask(null);
//		setDoTaskName(null);
//		setSitCodeDoorStop(0);
		client.reset();
	}

	@Override
	public void run() {
//		short count = 0;
		while (true) {
			try {
				//todo----tcp读------
//				if (client == null) {
//					client = new AGVTCPBaseComm(agv.getIp(), agv.getPort());
//				}
//				agv = soureInfo.getAgvService().selectById(agv.getId());
//				if (agv.getIsUse() != 1) {
//					continue;
//				}
//				agvRealInfo = (AGVPLCparamInfo) client.analysisMessage(AGVPLCparamInfo.class);
//				if(agvRealInfo==null){
//					logger.info("小车实时信息获取失败");
//					continue;
//				}
				//----------todo

				//查询任务是否完成
//				if (agvRealInfo.getSpecial_state() == 1) {
//					//0.1s记录一次
//					num++;
//				} else {
//					num = 0;
//				}
//				try {
//					// 当agvRealInfo.getWork_status()等于4，故障， 6：急停 7：激光 ，并且agvRealInfo.getError()不等于0时添加故障日志
//					if (agvRealInfo.getError() != 0) { //(agvRealInfo.getWork_status() == 4 || agvRealInfo.getWork_status() == 6 || agvRealInfo.getWork_status() == 7 )
//						int agvCode = agv.getCode();
//						String errorType = soureInfo.getAlarmLogMapper().queryAgvNewLog(agvCode);//最新的报警日志
//						if (errorType == null || Integer.parseInt(errorType) != agvRealInfo.getError()) {//不存在与前一次记录的相同的故障信息
//								AlarmLog alermLog = new AlarmLog(agvCode,getSiteCode(),String.valueOf(agvRealInfo.getError()),
//										changeType(agvRealInfo.getError()), new Date(),"1");
//							soureInfo.getAlarmLogMapper().insert(alermLog);//新增任务日志
//						}
//					}
//					if (agvRealInfo.getPointX() == 0 || agvRealInfo.getPointY() == 0) {
//						continue;
//					}
//					float x = agvRealInfo.getPointX();
//					float y = agvRealInfo.getPointY();
//					for (Sit sit : soureInfo.getSitList()) {
//						double value = sit.getRadius();//容错半径
//						if (((sit.getRealX() - value) <= x && (sit.getRealX() + value) >= x)
//								&& ((sit.getRealY() - value) <= y && (sit.getRealY() + value) >= y)) {
//							mapX = sit.getMapX();// svg坐标中的x值
//							mapY = sit.getMapY();// svg坐标中的y值
//							siteCode = sit.getCode();// 所在站点编号
//							break;
//						}
//					}
				} catch (Exception e) {
					logger.error("根据小车坐标信息获取站点信息失败：" + e.getMessage());
				}
				state = "已连接";
				updateTime = new Date();
// 			} catch (Exception e) {
//				logger.error("小车实时信息读取失败" + e.getMessage());
//			} finally {
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e) {
//				}
//			}
		}
	}
	
   //	 1：急停报警；(无需确认) 2：脱轨报警；（需确认）3：叉齿保护；（需确认）4：障碍报警；(无需确认)5：导航坐标保护；（需确认）6：取货限位报警；（需确认）
   //7：上位机通讯报警；（需确认）8：行走超程报警；(需任务清除)9：旋转角度异常报警；(需任务清除)10：DT35超限报警；（需确认）11：DT35目标值超限报警；(需任务清除)
   //12：DT35故障保护；(需任务清除)0：无报警信息 13:停车参数缺失报警
	public String changeType(int error) {
		String errorInfo=null;
		switch (error) {
		case 1:
			errorInfo="急停报警";
			break;
		case 2:
			errorInfo="脱轨报警";
			break;
		case 3:
			errorInfo="叉齿保护";
			break;
		case 4:
			errorInfo="障碍报警";
			break;
		case 5:
			errorInfo="导航坐标保护";
			break;
		case 6:
			errorInfo="取货限位报警";
			break;
		case 7:
			errorInfo="上位机通讯报警";
			break;
		case 8:
			errorInfo="行走超程报警";
			break;
		case 9:
			errorInfo="旋转角度异常报警";
			break;
		case 10:
			errorInfo="DT35超限报警";
			break;
		case 11:
			errorInfo="DT35目标值超限报警";
			break;
		case 12:
			errorInfo="DT35故障保护";
			break;
		case 13:
			errorInfo="停车参数缺失报警";
			break;
		default:
			break;
		}
		return errorInfo;
	}

//	public String laserSwitch(AGVInfo agvInfo) {
//		AGVPLCparamInfo agvplCparamInfo = agvInfo.getAgvRealInfo();
//		if (agvplCparamInfo == null) {
//			return null;
//		}
//		if (agvplCparamInfo.getPointX() == 0 || agvplCparamInfo.getPointY() == 0) {
//			return null;
//		}
//		float x = agvplCparamInfo.getPointX();  //当前实际坐标
//		float y = agvplCparamInfo.getPointY();
//		if (null != this.getTaskName() && !"".equals(this.getTaskName())) {
//			String split[] = this.getTaskName().split("_");
//			if (null != split && split.length == 2) {
//				if (null != split[1] && !"".equals(split[1])) {
//					if(Integer.valueOf(split[1])==111 || Integer.valueOf(split[1])==6777 || Integer.valueOf(split[1])==6888){
//						return null;
//					}
//					//拿split[1]查sit表，获取目的点xy
//					Sit sit = soureInfo.getSitMapper().querySit(Integer.valueOf(split[1]));
//					if (null != sit && sit.getRealX() != 0 && sit.getRealY() != 0) {
//						// TODO 计算两个点的距离
//						double juli = Math.sqrt(Math.abs((sit.getRealX() - x) * (sit.getRealX() - x)
//								+ (sit.getRealY() - y) * (sit.getRealY() - y)));
//						//x=2950 y=-7950
//						//2850  7650
//						//10000  90000
//						//100000
//						//316.22776601683796
////                        logger.info(new Date() + ":小车在x(" + x + "),y(" + y + "),目的点是:" + split[1]);
//						if (null != this.getClient()) {
//							if (juli <= 500 && agvplCparamInfo.getLaserState()!=0) {  //小于安全距离，并且激光是开启状态,下发关闭激光
//								if (this.getClient().SockSend(this.getClient().convertAgreement(54))) {
//									logger.info("距离目的点" + split[1] + "<=500,关闭后退激光,成功");
//								} else {
//									logger.info("关闭后退激光,失败");
//								}
//							} else if (juli > 500  && agvplCparamInfo.getLaserState()==0) {  //大于安全距离，并且激光是关闭状态,下发开启激光
//								if (this.getClient().SockSend(this.getClient().convertAgreement(55))) {
//									logger.info("距离目的点" + split[1] + ">500,开启后退激光,成功");
//								} else {
//									logger.info("开启后退激光,失败");
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}


}
