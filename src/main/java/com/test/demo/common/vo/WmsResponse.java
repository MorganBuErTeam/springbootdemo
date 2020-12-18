package com.test.demo.common.vo;

import lombok.Data;

/**
 * WMS 请求返回结果
 * @author Administrator
 *
 */
@Data
public class WmsResponse {
	private int state;     //接收情况,成功：200;失败500

}
