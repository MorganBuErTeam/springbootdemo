package com.test.demo.common.vo;

import lombok.Data;

/**
 * 上移下移对象
 * @author Administrator
 *
 */
@Data
public class UpOrDown {
	private int type;//1：上移，2：下移
	private int id;//对象id
	private int sort;//优先级
	public UpOrDown(int type, int id, int sort) {
		super();
		this.type = type;
		this.id = id;
		this.sort = sort;
	}
	public UpOrDown() {
		super();
	}
	
	
}
