
package com.test.demo.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 关于（格式：FKyyyyMMddxxxx的）流水号生成
 */
public class SerialNumberGeneration {
	private static final String SERIAL_NUMBER = "XXXX"; // 流水号格式
	private static SerialNumberGeneration primaryGenerater = null;

	private SerialNumberGeneration() {
	}

	/**
	 * 取得SerialNumberGeneration的单例实现
	 *
	 * @return
	 */
	public static SerialNumberGeneration getInstance() {
		if (primaryGenerater == null) {
			synchronized (SerialNumberGeneration.class) {
				if (primaryGenerater == null) {
					primaryGenerater = new SerialNumberGeneration();
				}
			}
		}
		return primaryGenerater;
	}

	/**
	 *
	 * 生成下一个编号
	 *
	 * @param sno
	 *            当前编号
	 * @param sign
	 *            编号前缀
	 * @return 下一个编号
	 */
	public synchronized String generaterNextNumber(String sno, String sign) {
		String id = null;
		Date date = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		// sign==null生成"XXXX"流水号格式
		// sign!=null生成（格式：FKyyyyMMddxxxx的）流水号
		if (StringUtils.isNotBlank(sno)) {
			// sno == null默认生成sign+yyyyMMdd+0001的流水号
			id = sign == null ? "0001" : (sign + formatter.format(date) + "0001");
		} else {
			DecimalFormat df = new DecimalFormat("0000");
			// 当长度小于14时产生"XXXX"流水号格式
			if (sno.length() < 14) {
				id = df.format(1 + Integer.parseInt(sno));
			} else {
				//当sno不是今日产生的订单号则产生sign+yyyyMMdd+0001的流水号
				if (Integer.parseInt(formatter.format(date))>Integer.parseInt(sno.substring(2,10))) {
					id = (sign + formatter.format(date) + "0001");
				}else{
					int count = SERIAL_NUMBER.length();
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < count; i++) {
						sb.append("0");
					}
					// sno ！= null生成sign+yyyyMMdd+xxxx的流水号
					id = sign == null ? df.format(1 + Integer.parseInt(sno))
							: (sign + formatter.format(date) + df.format(1 + Integer.parseInt(sno.substring(10, 14))));
				}
			}
		}
		return id;
	}
}