package com.test.demo.netty;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import com.test.demo.common.util.Convert;

/**
 * 柯金TCP通讯基础类
 * @author cy
 *
 */
public class AGVTCPBaseComm extends NettyTCPClient implements Serializable{

	private DatagramSocket socket;

	public AGVTCPBaseComm(String in_IP, int in_Port){
		super(in_IP, in_Port);//写
		try {
			socket = new DatagramSocket(8787);//读
		} catch (SocketException e) {
		}// 建立udp的服务 ，并且要监听一个端口。
	}

	/**
	 * 获取数据类型字节数
	 *
	 * @param types
	 *            数据类型
	 * @return 该类型对应的节数int
	 */
	private int calculateByteCount(String type) {
		int count = 0;
		if ("byte".equals(type)) {
			count = 1;
		} else if ("boolean".equals(type)) {
			count = 1;
		} else if ("short".equals(type)) {
			count = 2;
		} else if ("int".equals(type)) {
			count = 4;
		} else if (type.toLowerCase().indexOf("string") != -1) {
			count = 255;
		} else if ("double".equals(type)) {
			count = 8;
		} else if ("float".equals(type)) {
			count = 4;
		}
		return count;
	}

	/**
	 * Title: convertByteArr
	 * Description: 获取指定数组的指定的连续的元素
	 * @param startIndex 开始下标
	 * @param endIndex 结束下标
	 * @param b
	 * @return byte[]
	 */
	public static byte[] getByteArrayFromArr(int startIndex,int endIndex, byte[] b) {
		byte[] result = new byte[endIndex - startIndex];
		for (int i = 0; i < result.length; i++) {
			result[i] = b[i + startIndex];
		}
		return result;
	}

	/**
	 * Title: analysisMessage
	 * Description: 解析数据包
	 * @param clas
	 * @return
	 */
	public <T> Object analysisMessage(Class<T> clas) {
		Field[] fields = clas.getDeclaredFields();
		Object result = null;
		try {
			result = clas.newInstance();// 实例化结构体对象
			int[] counts = new int[fields.length + 1];// 对象属性字节数数组
			int count = 0;// 结构体总字节数
			counts[0] = 5;
			for (int i = 0; i < fields.length; i++) {
				int byteCount = 0;
				String type = fields[i].getGenericType().toString();// 获取对象属性数据类型
				if (type.indexOf("[") == -1) {// 如果不是数组
					byteCount = calculateByteCount(type.toLowerCase());// 获取非数据类型字节数
				}
				counts[i + 1] = byteCount;
				count += byteCount;
			}
			byte[] buf = new byte[count + counts[0]];// 准备空的数据包用于存放数据。
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length); // count
			// 调用udp的服务接收数据
			socket.receive(datagramPacket); // receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。
			// 数据实际上就是存储到了byte的自己数组中了。
			int index = counts[0];
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				String typeName = fields[i].getGenericType().toString();
				byte[] valBytes = getByteArrayFromArr(index, index + counts[i + 1], buf)  ;// 属性值对应的字节组
				index += counts[i + 1];
				if ("byte".equals(typeName)) {// 给对应的属性赋值
//					fields[i].set(result, Convert.ByteArrToByte(valBytes));
				} else if ("short".equals(typeName)) {
//					fields[i].set(result, Convert.ByteArrToShort(valBytes));
				} else if ("int".equals(typeName)) {
					fields[i].set(result, Convert.ByteArrToInt(valBytes));
				} else if (typeName.toLowerCase().indexOf("string") != -1) {
//					fields[i].set(result, Convert.ByteArrToString(valBytes));
				} else if ("double".equals(typeName)) {
//					fields[i].set(result, Convert.ByteArrToDouble(valBytes));
				} else if ("float".equals(typeName)) {
//					fields[i].set(result, Convert.ByteArrToFloat(valBytes));
				} else if ("boolean".equals(typeName)) {
//					fields[i].set(result, Convert.ByteArrToBool(valBytes));
				}
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	/***
	 * 发送指令
	 *
	 * @param btf
	 *            指令内容
	 */
	public boolean SockSend(byte[] bs ){
		try {
			this.getSocketChannel().writeAndFlush(bs);
			Thread.sleep(1000);	//预留1秒读取时间
//			System.out.println("123131返回指令："+this.getResult());
			if(("XYAGVS").equals(this.getResult())){
				logger.info("下发指令成功！");
				this.setResult("");
				return true;
			}else{
				logger.info("下发指令失败！");
				return false;
			}
		} catch (Exception e) {
			logger.info("下发指令失败！"+e.getMessage());
			return false;
		}
	}



	/**
	 * 启动
	 */
	public boolean start(){
		return SockSend(convertAgreement(1));
	}

	/**
	 * 停止
	 */
	public boolean stop(){
		return SockSend(convertAgreement(2));
	}

	/**
	 * 重置/清空
	 */
	public boolean reset(){
		return SockSend(convertAgreement(9));
	}

	/**
	 * 强制完成
	 */
	public boolean forceComplete(){
		return SockSend(convertAgreement(53));
	}

	/**
	 * 协议转换
	 * @param type 类型  1-启动  2-停止  9-复位  53-强制完成
	 * @return
	 */
	public byte[] convertAgreement(int type){
		byte[] mByteArray = new byte[9];		//协议byte数组   XYAGV1
		ByteBuffer byteBuffer = ByteBuffer.wrap(mByteArray);
		String agreement = "XYAGV";	//协议
		byteBuffer.put(agreement.getBytes());
		byteBuffer.put(Convert.IntToByteArr(type));
		this.logger.info("给小车下发指令：" + agreement + type);
		return mByteArray;
	}
}
