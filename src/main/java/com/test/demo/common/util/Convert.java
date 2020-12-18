package com.test.demo.common.util;

public class Convert {
	
	/**
	 * Title: IntToByteArr
	 * Description: int转byte数组
	 * @param data
	 * @return byte[]
	 */
	public static byte[] IntToByteArr(int data) {
		byte base = -1;
		byte[] result = { (byte) (data & base), 
				(byte) (data >> 8 & base), 
				(byte) (data >> 16 & base),
				(byte) (data >> 24 & base) };
		return result;
	}

	/**
	 * Title: ByteArrToInt
	 * Description: byte数组转int
	 * @param data
	 * @return int
	 */
	public static int ByteArrToInt(byte[] data) {
		int expectedSize = 4;
		if (data.length != expectedSize) {
			throw new IllegalArgumentException(
					"The lenght of the array is not equal to the expected size " + expectedSize);
		}
		int base = 255;
		int result = data[3] & base;
		result = result << 8 | data[2] & base;
		result = result << 8 | data[1] & base;
		result = result << 8 | data[0] & base;
		return result;
	}
}
