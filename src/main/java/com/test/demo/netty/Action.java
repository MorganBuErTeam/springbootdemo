package com.test.demo.netty;

public class Action {
	
	private int number;						//编号,-1代表该节点不存在
	
	private int seg_number;					//归属的片段编号，尽量归类
	
	private int speed;						//速度
	
	private int timer;						//定时器
	
	private Point point;					//坐标
	
	private int action[];		//停止定时启动顶升降落调速，初始为-1，具体如下，注意顺序	，如【1,4，-1，-1，-1】，代表先停止后，举升升起

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSeg_number() {
		return seg_number;
	}

	public void setSeg_number(int seg_number) {
		this.seg_number = seg_number;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int[] getAction() {
		return action;
	}

	public void setAction(int[] action) {
		this.action = action;
	}

	public Action(int number, int seg_number, int speed, int timer, int[] action, Point point) {
		super();
		this.number = number;
		this.seg_number = seg_number;
		this.speed = speed;
		this.timer = timer;
		this.point = point;
		this.action = action;
	}
}
