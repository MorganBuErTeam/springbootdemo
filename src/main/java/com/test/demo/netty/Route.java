package com.test.demo.netty;

import lombok.Data;

@Data
public class Route {
	
	int bflag;				//1:line,3:arc，:无信息
	int seg_number;   		//片段编号:0~1000 
	int move_direction;		//运动方向，：理论前进，-1：理论后退；	
	Point startpoint;	//片段的起始端点，直线和圆弧
	Point endpoint;		//片段的终止端点，直线和圆弧
	Point arccenter;		//arc center point
	int radius;				//圆弧半径
	int rot_angle;   		//*10000，圆弧的旋转弧度（顺时针为正，逆时针为负）
	int length;          	//线段或圆弧的长度	
	int segLaserSelect;		//激光扫描仪通道选择：0,1,2
	
	public Route(int bflag, int seg_number, int move_direction, Point startpoint
			, Point endpoint,Point arccenter, int segLaserSelect) {
		super();
		this.bflag = bflag;
		this.seg_number = seg_number;
		this.move_direction = move_direction;
		this.startpoint = startpoint;
		this.endpoint = endpoint;
		this.arccenter = arccenter;
		this.segLaserSelect = segLaserSelect;
	}
	
	public Route() {
		super();
	}
	
}
